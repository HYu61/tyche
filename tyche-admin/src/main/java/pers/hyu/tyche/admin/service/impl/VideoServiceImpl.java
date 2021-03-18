package pers.hyu.tyche.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.hyu.tyche.admin.mapper.UsersReportMapperCustomized;
import pers.hyu.tyche.admin.mapper.VideosMapper;
import pers.hyu.tyche.admin.mapper.VideosMapperCustomized;
import pers.hyu.tyche.admin.pojo.UsersReport;
import pers.hyu.tyche.admin.pojo.Videos;
import pers.hyu.tyche.admin.pojo.dto.UserReportDto;
import pers.hyu.tyche.admin.pojo.dto.VideoDto;
import pers.hyu.tyche.admin.service.VideoService;
import pers.hyu.tyche.utils.PagedResult;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private UsersReportMapperCustomized usersReportMapperCustomized;

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private VideosMapperCustomized videosMapperCustomized;


    @Override
    public PagedResult selectAllReportedVideos(Integer page, Integer limit) {
        PageHelper.startPage(page, 10);
        List<UserReportDto> userReportDtoList = usersReportMapperCustomized.selectAllReports();
        PageInfo<UserReportDto> pageInfo = new PageInfo<>(userReportDtoList);

        PagedResult result = new PagedResult();
        result.setTotal(pageInfo.getPages());
        result.setRows(userReportDtoList);
        result.setPage(page);
        result.setRecords(pageInfo.getTotal());

        return result;
    }

    @Override
    public PagedResult selectAllVideos(VideoDto videoDto, Integer page, Integer limit) {
        String username = videoDto == null ? "" : videoDto.getUsername();
        Integer status = videoDto == null ? null: videoDto.getStatus();

        PageHelper.startPage(page, 10);
        List<VideoDto> videoDtoList = videosMapperCustomized.selectAllVideo(username, status);
        PageInfo<VideoDto> pageInfo = new PageInfo<>(videoDtoList);

        PagedResult result = new PagedResult();
        result.setTotal(pageInfo.getPages());
        result.setRows(videoDtoList);
        result.setPage(page);
        result.setRecords(pageInfo.getTotal());

        return result;
    }

    @Override
    public int updateVideoStatus(String videoId, Integer operation) {
        Videos updateVideo = new Videos();
        updateVideo.setId(videoId);
        updateVideo.setStatus(operation);
        int result = videosMapper.updateByPrimaryKeySelective(updateVideo);
        return result;
    }
}
