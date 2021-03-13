package pers.hyu.tyche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pers.hyu.tyche.dao.*;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.enums.VideoStatus;
import pers.hyu.tyche.exception.UserServiceException;
import pers.hyu.tyche.pojo.entity.UsersLikedVideos;
import pers.hyu.tyche.pojo.entity.UsersLikedVideosExample;
import pers.hyu.tyche.pojo.entity.Videos;
import pers.hyu.tyche.pojo.entity.VideosExample;
import pers.hyu.tyche.pojo.model.dto.VideoDto;
import pers.hyu.tyche.service.VideoService;
import pers.hyu.tyche.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Heng Yu
 * @version 1.0
 * @see pers.hyu.tyche.service.VideoService
 * @see pers.hyu.tyche.service.UploadFileService
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Value("${file-space}")
    private String fileSpace;

    @Value("${ffmpeg-exe}")
    private String ffmpegExe;

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private VideosMapperCustomized videosMapperCustomized;

    @Autowired
    private UsersLikedVideosMapper usersLikedVideosMapper;

    @Autowired
    private UsersMapperCustomized usersMapperCustomized;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private EntityUtils entityUtils;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    @Override
    public void generateCoverImage(String inputVideoPath, String outputCoverPath) throws IOException {
        String coverAbsPath = fileSpace + outputCoverPath;

        // create the folders if the folder does not exists
        File coverFile = new File(coverAbsPath);
        if (!coverFile.getParentFile().exists()) {
            coverFile.getParentFile().mkdirs();
        }

        FfmpegUtil ffmpegUtil = new FfmpegUtil(ffmpegExe);
        ffmpegUtil.generateCover(fileSpace + inputVideoPath, fileSpace + outputCoverPath);
    }

    @Override
    public void combinedWithBgm(String inputVideoPath, String inputBgmPath, float duration, String outputVideoPath) throws IOException {
        FfmpegUtil ffmpegUtil = new FfmpegUtil(ffmpegExe);
        String inputVideoAbsPath = fileSpace + inputVideoPath;
        String tempVideoAbsPath = fileSpace + outputVideoPath;
        String bgmAbsPath = fileSpace + inputBgmPath;

        // create the folders if the folder does not exists
        File tempVideoFile = new File(tempVideoAbsPath);
        if (!tempVideoFile.getParentFile().exists()) {
            tempVideoFile.getParentFile().mkdirs();
        }

        ffmpegUtil.removeAudio(inputVideoAbsPath, tempVideoAbsPath);
        ffmpegUtil.combineVideoWithBgm(tempVideoAbsPath, bgmAbsPath, duration, inputVideoAbsPath);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addVideo(VideoDto videoDto) {
        Videos uploadVideo = new Videos();
        BeanUtils.copyProperties(videoDto, uploadVideo);
        uploadVideo.setId(entityUtils.generateId()); // set the id
        uploadVideo.setCreateTime(new Date()); // set the created time

        // if the video is vip video then the status is 1; otherwise is 0
        uploadVideo.setStatus(videoDto.getVipVideo() ? VideoStatus.VIP_VIDEO.getVideoStatus() : VideoStatus.REGULAR_VIDEO.getVideoStatus());
        videosMapper.insertSelective(uploadVideo);

    }

    @Transactional(readOnly = true)
    @Override
    public PagedResult getVideoCovers(Integer page, Integer limit, String searchContent) {
        String search = searchContent == null ? "" : searchContent;

        // mybatis pagination
        PageHelper.startPage(page, limit);

        List<VideoDto> videoDtoList = videosMapperCustomized.selectVideoCovers(search);

        PageInfo<VideoDto> pageInfo = new PageInfo<>(videoDtoList);

        // encapsulated the the info of the pagination videos
        PagedResult result = new PagedResult();
        result.setPage(page); // start page
        result.setRecords(pageInfo.getTotal()); // the num of total records
        result.setTotal(pageInfo.getPages()); // the num of total pages
        result.setRows(videoDtoList); // the records in one page
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public PagedResult getVideoCoversByPublisher(String userId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);

        // orders
        List<VideoDto> videosList = videosMapperCustomized.selectVideoCoversByUserId(userId);
        PageInfo<VideoDto> pageInfo = new PageInfo<>(videosList);

        PagedResult result = new PagedResult();
        result.setPage(page);
        result.setRecords(pageInfo.getTotal());
        result.setTotal(pageInfo.getPages());
        result.setRows(videosList);

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public PagedResult getVideoCoversByUserLiked(String userId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);

        List<VideoDto> videosList = videosMapperCustomized.selectUserLikedVideoCovers(userId);
        PageInfo<VideoDto> pageInfo = new PageInfo<>(videosList);

        PagedResult result = new PagedResult();
        result.setPage(page);
        result.setRecords(pageInfo.getTotal());
        result.setTotal(pageInfo.getPages());
        result.setRows(videosList);

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public VideoDto getVideoById(String videoId, String loginUserId) {
        VideoDto videoDto = videosMapperCustomized.selectVideoById(videoId);

        if (videoDto != null && loginUserId != null) {
            UsersLikedVideosExample usersLikedVideosExample = new UsersLikedVideosExample();
            usersLikedVideosExample.createCriteria().andUserIdEqualTo(loginUserId)
                    .andVideoIdEqualTo(videoId);
            PageHelper.startPage(0, 1);  // limit 1
            List<UsersLikedVideos> usersLikedVideosList = usersLikedVideosMapper.selectByExample(usersLikedVideosExample);

            videoDto.setLoginUserLikeTheVideo(usersLikedVideosList.size() > 0);
        }
        return videoDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void likeVideo(String loginUserId, String videoId, String videoPublisherId) {
        // 1. add record into the user_liked_videos table
        UsersLikedVideos usersLikedVideo = new UsersLikedVideos();
        usersLikedVideo.setId(entityUtils.generateId());
        usersLikedVideo.setUserId(loginUserId);
        usersLikedVideo.setVideoId(videoId);
        usersLikedVideosMapper.insert(usersLikedVideo);

        // 2. update the like_counts +1 in videos table
        videosMapperCustomized.addLikeCounts(videoId);

        // 3. update the received_like_counts +1 in users table
        usersMapperCustomized.addReceivedLikeCount(videoPublisherId);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void dislikeVideo(String loginUserId, String videoId, String videoPublisherId) {
        // 1. remove record into the user_liked_videos table
        UsersLikedVideosExample example = new UsersLikedVideosExample();
        example.createCriteria().andUserIdEqualTo(loginUserId).andVideoIdEqualTo(videoId);
        usersLikedVideosMapper.deleteByExample(example);

        // 2. update the like_counts -1 in videos table
        videosMapperCustomized.reduceLikeCounts(videoId);

        // 3. update the received_like_counts -1 in users table
        Map<String, Object> map = new HashMap<>();
        map.put("userId", videoPublisherId);
        map.put("count", 1);
        usersMapperCustomized.reduceReceivedLikeCount(map);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int editVideo(String videoId, String videoDesc, boolean vipVideo) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setVideoDesc(videoDesc);
        video.setStatus(vipVideo ? VideoStatus.VIP_VIDEO.getVideoStatus() : VideoStatus.REGULAR_VIDEO.getVideoStatus());

        Integer videoOriginalStatus = videosMapper.selectByPrimaryKey(videoId).getStatus();
        if(videoOriginalStatus == null){
            return 0; // no such video
        }else if(videoOriginalStatus == VideoStatus.INACTIVE_VIDEO.getVideoStatus()){
            return -1; // video is inactive
        }
        videosMapper.updateByPrimaryKeySelective(video);
        return 1; // updated the video
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int removeVideo(String videoId) {
        // get video like counts
        Videos video = videosMapper.selectByPrimaryKey(videoId);
        if (video == null) {
            return -1;
        }

        int likeCount = video.getLikeCounts();
        String filePath = video.getVideoPath();
        String fileCoverPath = video.getCoverPath();

        // update the user received like counts
        String publisherId = video.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", publisherId);
        map.put("count", likeCount);
        usersMapperCustomized.reduceReceivedLikeCount(map);

        // delete the user like video relationship
        UsersLikedVideosExample example = new UsersLikedVideosExample();
        example.createCriteria().andVideoIdEqualTo(videoId);
        usersLikedVideosMapper.deleteByExample(example);

        // delete the video from the db
        int result = videosMapper.deleteByPrimaryKey(videoId);

        //delete the file from the server
        deleteFolderOrFile(publisherId, filePath);
        deleteFolderOrFile(publisherId, fileCoverPath);
        return result;
    }

    @Override
    public void initFolder(String userId, String[] folderNames) {

    }

    /**
     * Upload the video.
     * When the new video uploaded, the old video won't be deleted.
     *
     * @param file The video need to be upload.
     * @param path The relative path for the upload video need to be saved.
     * @return The UploadResult that contain the new name of the file and the filename without the extension.
     * @throws IOException
     */
    @Override
    public UploadResult uploadFile(MultipartFile file, String path) throws IOException {
        // upload the file
        String destFolder = fileSpace + path;
        UploadResult result = fileUploadUtil.uploadFile(file, destFolder, false, true);
        return result;
    }


    /**
     * Delete the video and the cover image of the video.
     *
     * @param userId     In which user's folder.
     * @param fileName The name of the video.
     */
    @Override
    public void deleteFolderOrFile(String userId, String fileName) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fileName)) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        String deleteAbsFile = fileSpace + fileName;
        File deleteFile = new File(deleteAbsFile);
        if (deleteFile.isFile() ) {
            deleteFile.delete();
        }
    }
}
