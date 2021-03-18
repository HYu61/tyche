package pers.hyu.tyche.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.enums.FolderNameEnum;
import pers.hyu.tyche.enums.MediaTypeEnum;
import pers.hyu.tyche.pojo.model.dto.VideoDto;
import pers.hyu.tyche.pojo.model.request.UserRelationModel;
import pers.hyu.tyche.pojo.model.request.VideoInfoEditModel;
import pers.hyu.tyche.pojo.model.request.VideoInfoModel;
import pers.hyu.tyche.pojo.model.request.VipVideoAccessAnswerModel;
import pers.hyu.tyche.service.BgmService;
import pers.hyu.tyche.service.UserService;
import pers.hyu.tyche.service.VideoService;
import pers.hyu.tyche.service.VipVideoAccessService;
import pers.hyu.tyche.utils.EntityUtils;
import pers.hyu.tyche.utils.PagedResult;
import pers.hyu.tyche.utils.ResponseEnvelope;
import pers.hyu.tyche.utils.UploadResult;

import java.io.IOException;

/**
 * Video API
 *
 * @author Heng Yu
 * @version 1.0
 */
@Api(tags = "Video API")
@RestController
public class VideoController {
    @Value("${pagehelper.limit}")
    private Integer limit;

    @Autowired
    private EntityUtils entityUtils;
    @Autowired
    private BgmService bgmService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;
    @Autowired
    private VipVideoAccessService vipVideoAccessService;


    /**
     * Upload the video and generator the cover image of the video.
     * Also can combine the bgm to the video according to the info from the form data.
     * Save the video and cove relative path into DB.
     *
     * @param file           The video need to upload.
     * @param videoInfoModel A model that contains the info of the video from the form-data.
     * @return The response of the upload video request.
     * @throws IOException
     */
    @ApiOperation(value = "Upload the video to the server")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(value = "/videos", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseEnvelope<?>> uploadVideo(@ApiParam(value = "video need to be upload", required = true)
                                                           @RequestPart MultipartFile file,
                                                           @ModelAttribute VideoInfoModel videoInfoModel
    ) throws IOException, IllegalAccessException {
        // check if the request field is valid
        if (file == null || entityUtils.isNotAllFieldSet(videoInfoModel, true)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }
        String userId = videoInfoModel.getUserId();
        if (!userService.isUserExists(userId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }

        // set the path for save the video c:/tyche/users/id/videos
        String videoRelativeFolder = videoInfoModel.getVipVideo() ?
                userService.getUserUploadFolder(userId, FolderNameEnum.VIP.getFolderName())
                : userService.getUserUploadFolder(userId, FolderNameEnum.VIDEOS.getFolderName());

        String coverRelativeFolder = userService.getUserUploadFolder(userId, FolderNameEnum.COVER.getFolderName());
//        String separator = FileSystems.getDefault().getSeparator();
        String separator = "/";

        // upload the video
        UploadResult result = videoService.uploadFile(file, videoRelativeFolder);

        // error for upload
        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage()));
        }

        // generate cover image for the video
        String inputVideoRelPath = new StringBuilder()
                .append(videoRelativeFolder).append(separator)
                .append(result.getFileName())
                .toString();
        String coverImageRelPath = new StringBuilder()
                .append(coverRelativeFolder).append(separator)
                .append(result.getFileNameWithoutExtension())
                .append(".").append(MediaTypeEnum.COVER_IMAGE.getFormat())
                .toString();
        videoService.generateCoverImage(inputVideoRelPath, coverImageRelPath);


        // combine the background music with the original video
        String bgmId = videoInfoModel.getBgmId();
        if (StringUtils.isNotBlank(bgmId)) {
            String bgmRelPath = bgmService.getById(bgmId).getPath();

            String tempVideoRelPath = userService.getUserUploadFolder(userId, FolderNameEnum.TEMP.getFolderName()) + separator + result.getFileName();

            videoService.combinedWithBgm(inputVideoRelPath, bgmRelPath, videoInfoModel.getVideoSeconds(), tempVideoRelPath);
        }

        // get the path for db
        VideoDto videoDto = new VideoDto();
        BeanUtils.copyProperties(videoInfoModel, videoDto);

//        videoDto.setUserId(userId);
        videoDto.setVideoPath(inputVideoRelPath);
        videoDto.setCoverPath(coverImageRelPath);
        videoDto.setBmgId(bgmId);

        videoService.addVideo(videoDto);

        return ResponseEntity.ok().body(null);
    }

    /**
     * Get the list of the covers of the video according to the search content.
     *
     * @param page          The num for pagination.
     * @param searchContent The content for search; if it is null, will get all covers.
     * @return The list of the covers of the videos.
     */
    @ApiOperation(value = "Get the list of the covers of the videos",
            notes = "Get the pagination list of the video covers according to the search from the description of the video or the publisher's nickname. " +
                    "If there is no search content, get all the covers")
    @GetMapping(value = "/videos/covers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseEnvelope<?>> getAllVideoCover(@RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) String searchContent) {
        PagedResult result = videoService.getVideoCovers(page == null ? 1 : page, limit, searchContent);
        return ResponseEntity.ok(ResponseEnvelope.ok(result));

    }

    /**
     * Get all the covers of the videos from the video publisher.
     *
     * @param userId The id of the video publisher.
     * @param page   The num of the pagination.
     * @return The list of the covers of the videos.
     */
    @ApiOperation(value = "Get all the covers of the videos from the video publisher.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @GetMapping(value = "/users/{publisherId}/videos/covers")
    public ResponseEntity<ResponseEnvelope<?>> getVideoCoversByPublisher(@PathVariable(name = "publisherId") String userId,
                                                                         @RequestParam(required = false) Integer page) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }
        PagedResult result = videoService.getVideoCoversByPublisher(userId, page == null ? 1 : page, limit);
        return ResponseEntity.ok(ResponseEnvelope.ok(result));
    }

    /**
     * Get all the covers of the videos that the user liked.
     *
     * @param userId The id of the login user.
     * @param page   The num of the pagination.
     * @return The list of the covers of the videos.
     */
    @ApiOperation(value = "Get all the covers of the videos that the login user liked.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @GetMapping(value = "/users/{userId}/videos/liked/covers")
    public ResponseEntity<ResponseEnvelope<?>> getVideoCoversByUserLiked(@PathVariable String userId,
                                                                         @RequestParam(required = false) Integer page) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }
        PagedResult result = videoService.getVideoCoversByUserLiked(userId, page == null ? 1 : page, limit);
        return ResponseEntity.ok(ResponseEnvelope.ok(result));
    }


    /**
     * Get the detail of the regular video.
     *
     * @param videoId     The id of the video.
     * @param loginUserId The id of the login user.
     * @return The detail of the video.
     */
    @ApiOperation(value = "Get the detail info of the videos")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @GetMapping(value = "/videos/{videoId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseEnvelope<?>> getVideoDetail(@PathVariable String videoId, @RequestParam(required = false) String loginUserId) {
        if (StringUtils.isBlank(videoId)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }
        // get the info of the upper of the video
        VideoDto result = videoService.getVideoById(videoId, loginUserId);
        if (result == null) {
            return ResponseEntity.noContent().build();
        }
        // get if the user like the video
        return ResponseEntity.ok(ResponseEnvelope.ok(result));
    }


    /**
     * Get the detail of the vip video.
     * Only vip user can access the video.
     *
     * @param videoId     The id of the video.
     * @param loginUserId The id of the login user.
     * @return The detail of the video.
     */
    @ApiOperation(value = "Get the detail info of the vip videos")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @GetMapping(value = "/vip_videos/{videoId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseEnvelope<?>> getVipVideoDetail(@PathVariable String videoId, @RequestParam String publisherId, @RequestParam String loginUserId) {
        if (StringUtils.isBlank(videoId)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        VideoDto result = videoService.getVideoById(videoId, loginUserId);
        if (result == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ResponseEnvelope.ok(result));
    }


    /**
     * The login user send the vip video access answer. If it corrects, then the user can access the vip video.
     *
     * @param vipVideoAccessAnswerModel The model includes the video publihserId, the loginUserId, and the answer.
     * @return The response for if the answer correct.
     */
    @ApiOperation(value = "Send the vip video access answer for get the right to access the vip video.")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
    @ApiResponse(code = 403, message = "The answer is incorrect!")})
    @PostMapping("/vip_videos/access_relationships")
    public ResponseEntity<ResponseEnvelope<?>> addVipVideoAccess(@RequestBody VipVideoAccessAnswerModel vipVideoAccessAnswerModel) throws IllegalAccessException {
        if (entityUtils.isNotAllFieldHasText(vipVideoAccessAnswerModel)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-400, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));

        }
        int result = vipVideoAccessService.addUserAccess(vipVideoAccessAnswerModel.getPublisherId(),
                vipVideoAccessAnswerModel.getLoginUserId(),
                vipVideoAccessAnswerModel.getVipVideoAccessAnswer());

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseEnvelope.error(-1, ErrorMessages.ANSWER_INCORRECT.getErrorMessage()));

        } else if (result == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseEnvelope.error(-1, ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage()));

        } else {
            return ResponseEntity.ok(ResponseEnvelope.ok(null));
        }

    }


    /**
     * If the login user like the video, the videoLikeCount will be increased by 1, and record the user like the video into db.
     * Otherwise, the videoLikeCount will be decreased by 1, and delete the relationship between the user and the video.
     *
     * @param videoId           The id of the video.
     * @param userRelationModel The request object include the loginUserId, the videoPublisherId, and weather the loginUser like the video.
     * @return The result of the request.
     * @throws IllegalAccessException
     */
    @ApiOperation(value = "Like or dislike the video.")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @PutMapping(value = "/videos/{videoId}/like_count")
    public ResponseEntity<ResponseEnvelope<?>> likeOrDislikeVideo(@PathVariable String videoId, @RequestBody UserRelationModel userRelationModel) throws IllegalAccessException {
        if (entityUtils.isNotAllFieldSet(userRelationModel, false)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        if (userRelationModel.isLikeVideoOrFollowPublisher()) {

            // like the video
            videoService.likeVideo(userRelationModel.getLoginUserId(), videoId, userRelationModel.getVideoPublishUserId());
        } else {

            // dislike the video
            videoService.dislikeVideo(userRelationModel.getLoginUserId(), videoId, userRelationModel.getVideoPublishUserId());
        }

        return ResponseEntity.ok().body(ResponseEnvelope.ok(null));
    }


    /**
     * Edit the info of the video.
     * Include the description and the status of the video.
     *
     * @param videoId            The id of which video need to be edited.
     * @param videoInfoEditModel The model include the info of the video needed to be edited.
     * @return
     * @throws IllegalAccessException
     */
    @ApiOperation(value = "Edit the video.", notes = "Edit the description of the video or the status of the video.")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 403, message = "Video is inactive, can not be edited!")
    })
    @PutMapping("/videos/{videoId}")
    public ResponseEntity<ResponseEnvelope<?>> editVideo(@PathVariable String videoId,
                                                         @RequestBody VideoInfoEditModel videoInfoEditModel) throws IllegalAccessException {

        if (StringUtils.isBlank(videoId) || entityUtils.isNotAllFieldSet(videoInfoEditModel, true)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        int result = videoService.editVideo(videoId, videoInfoEditModel.getVideoDesc(), videoInfoEditModel.getVipVideo());
        if (result == -1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseEnvelope.error(-1, "Video is inactive, can not edit!"));
        }
        return ResponseEntity.ok(ResponseEnvelope.ok(null));

    }


    /**
     * Delete the video.
     *
     * @param videoId The id of the video that need to be deleted.
     * @return
     */
    @ApiOperation(value = "Delete the video.", notes = "Delete the video, the user_like_video relationship and decrease the like count.")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @DeleteMapping("/videos/{videoId}")
    public ResponseEntity<ResponseEnvelope<?>> removeVideo(@PathVariable String videoId) {
        if (StringUtils.isBlank(videoId)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        int result = videoService.removeVideo(videoId);
        if (result < 1) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseEnvelope.error(-1, ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage()));
        }
        return ResponseEntity.ok(ResponseEnvelope.ok(null));
    }


}
