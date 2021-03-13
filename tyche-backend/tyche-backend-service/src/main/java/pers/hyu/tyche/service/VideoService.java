package pers.hyu.tyche.service;

import pers.hyu.tyche.pojo.model.dto.VideoDto;
import pers.hyu.tyche.utils.PagedResult;

import java.io.IOException;

/**
 * The interface is the CRUD operations for the video entity.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface VideoService extends UploadFileService {

    /**
     * Generate a cover image of the video.
     *
     * @param inputVideoPath  The relative path of the video.
     * @param outputCoverPath The relative path of the cover image that need to be saved.
     * @throws IOException
     */
    void generateCoverImage(String inputVideoPath, String outputCoverPath) throws IOException;

    /**
     * Combine the background music and the upload video into a new video.
     *
     * @param inputVideoPath  The relative path of the original video.
     * @param inputBgmPath    The relative path of the background music.
     * @param duration        The seconds of the original video.
     * @param outputVideoPath The relative path of the new combined video.
     * @throws IOException
     */
    void combinedWithBgm(String inputVideoPath, String inputBgmPath, float duration, String outputVideoPath) throws IOException;


    /**
     * Add a new upload video into the DB.
     *
     * @param videoDto The VideoDto object that contains the info of the video uploaded.
     */
    void addVideo(VideoDto videoDto);


    /**
     * Get the cover image of the video from the DB.
     * If the content of the search is null, the method will get all the videos cover;
     * otherwise, get the video's cover according to the search content from the description or the nickname.
     *
     * @param page  The num of from which page to start select the data.
     * @param limit The num of how many records will display in one page.
     * @return The pagination of the list of the video's cover.
     */
    PagedResult getVideoCovers(Integer page, Integer limit, String searchContent);

    /**
     * Get all the video cover image from the publisher.
     *
     * @param userId The userId of the video publisher.
     * @param page   The num of from which page to start select the data.
     * @param limit  The num of how many records will display in one page.
     * @return The pagination of the list of the video's cover.
     */
    PagedResult getVideoCoversByPublisher(String userId, Integer page, Integer limit);

    /**
     * Get all the video cover image that the user liked.
     *
     * @param userId The id of the user.
     * @param page   The num of from which page to start select the data.
     * @param limit  The num of how many records will display in one page.
     * @return The pagination of the list of the video's cover.
     */
    PagedResult getVideoCoversByUserLiked(String userId, Integer page, Integer limit);


    /**
     * Get the detail of the video according to the id of the video.
     * Also get the info that the login user if like the video.
     *
     * @param videoId     The id of the video.
     * @param loginUserId The id of the login user.
     * @return The detail of the video, and if the login user like the video.
     */
    VideoDto getVideoById(String videoId, String loginUserId);

    /**
     * The login user like the video will increase the like-counts of the video, and save the record in to the db.
     *
     * @param loginUserId The id of the login user.
     * @param videoId The id of the video that the user liked.
     * @param videoPublisherId The id of the video publisher.
     */
    void likeVideo(String loginUserId, String videoId, String videoPublisherId);

    /**
     * The login user dislike the video will decrease the like-counts of the video, and remove the record in to the db.
     *
     * @param loginUserId The id of the login user.
     * @param videoId The id of the video that the user liked.
     * @param videoPublisherId The id of the video publisher.
     */
    void dislikeVideo(String loginUserId, String videoId, String videoPublisherId);

    /**
     * Update the info of the video.
     *
     * @param videoId The id of the video needs to be edited.
     * @param videoDesc The new description of the video.
     * @param vipVideo Choose if set the video as vip video; only vip user can set vip video.
     * @return -1, the video is inactive that can not be edited. 0, there is no such video. 1 successful edited the video.
     */
    int editVideo(String videoId, String videoDesc, boolean vipVideo);

    /**
     * Delete the video.
     * When the user delete the video, the video and the user like the video relationship are deleted; besides, the like count will decrease.
     *
     * @param videoId The id of the video need to be deleted.
     * @return 1 if success; otherwise failed.
     */
    int removeVideo(String videoId);
//
//    void addComment(String videoId, CommentModel commentModel);
//
//
//   PagedResult getAllComments(String videoId, Integer page, Integer pageSize);

}
