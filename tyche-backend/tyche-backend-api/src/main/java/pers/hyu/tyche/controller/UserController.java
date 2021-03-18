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
import pers.hyu.tyche.enums.UserStatus;
import pers.hyu.tyche.pojo.model.dto.UserDto;

import pers.hyu.tyche.pojo.model.request.UserEditModel;
import pers.hyu.tyche.pojo.model.request.UserRegisterAndLoginModel;
import pers.hyu.tyche.pojo.model.request.UserRelationModel;
import pers.hyu.tyche.pojo.model.response.UserResponseModel;
import pers.hyu.tyche.service.UserService;
import pers.hyu.tyche.utils.EntityUtils;
import pers.hyu.tyche.utils.PagedResult;
import pers.hyu.tyche.utils.ResponseEnvelope;
import pers.hyu.tyche.utils.UploadResult;

import java.io.IOException;
import java.util.List;

/**
 * The User API.
 *
 * @author Heng Yu
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
@Api(tags = "Users API")
public class UserController {

    @Value("${pagehelper.limit}")
    private Integer limit;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityUtils entityUtils;


    /**
     * Register a new user.
     *
     * @param userRequestModel The user info model including the field of username and password for the request.
     * @return The response of the register request.
     * @throws Exception
     */
    @ApiOperation(value = "Create a new user", notes = "Register a new user by username and password")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 409, message = "Username already exists")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelope<?>> createUser(@ApiParam(name = "user register model")
                                                          @RequestBody UserRegisterAndLoginModel userRequestModel) throws Exception {

        // check if the username, password, security answer field are null
        if (entityUtils.isNotAllFieldHasText(userRequestModel)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        // check if the user is already exists
        if (userService.isUserExists(userRequestModel.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(ResponseEnvelope.error(-1, ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage()));
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequestModel, userDto);

        // save the info into DB
        String userId = userService.registerUser(userDto);

        // init the folders for the profile, the video, the cover image and the temp
        String[] folders = {FolderNameEnum.PROFILE.getFolderName(),
                FolderNameEnum.COVER.getFolderName(),
                FolderNameEnum.TEMP.getFolderName()};
        userService.initFolder(userId, folders);

        return ResponseEntity.ok(null);
    }

    /**
     * Reset the user's password
     *
     * @param userRequestModel The user info model including the field of username, password and the security answer for the request.
     * @return The response of the reset password request.
     * @throws Exception
     */
    // TODO Patching
    @ApiOperation(value = "Reset the password", notes = "Reset the password need to provide the username and the security answer to validate!")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @PutMapping("/password")
    public ResponseEntity<ResponseEnvelope<?>> resetPassword(@RequestBody UserRegisterAndLoginModel userRequestModel) throws Exception {
        // check if the username and password field are null
        if (entityUtils.isNotAllFieldHasText(userRequestModel)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        // if the security answer correct, reset the password
        String username = userRequestModel.getUsername();
        String securityAnswer = userRequestModel.getSecurityAnswer();
        String newPassword = userRequestModel.getPlainPassword();
        boolean success = userService.resetPassword(username, securityAnswer, newPassword);
        if (!success) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body(ResponseEnvelope.error(-1, ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage()));

        }
        return ResponseEntity.ok(null);
    }

    /**
     * Get the user info according to the userId.
     *
     * @param userId The id of the user.
     * @return The response of the get user request that contains the user info; if there is no such user, return null.
     */
    @ApiOperation(value = "Get the user info")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content Found"),
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseEnvelope<?>> getUser(@PathVariable String userId, @RequestParam(required = false) String followerId) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }
        UserDto userDto = userService.getUserById(userId);

        if (userDto == null) {
            // return httpStatus: 204 if there is user founded
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        UserResponseModel userDetail = new UserResponseModel();
        BeanUtils.copyProperties(userDto, userDetail);

        if (StringUtils.isNotBlank(followerId)) {
            userDetail.setHasFollowed(userService.isFollowed(followerId, userId));
        }
        return ResponseEntity.ok(ResponseEnvelope.ok(userDetail));
    }


    /**
     * Get the user's follows' info.
     *
     * @param userId The id of the user.
     * @param page The num of the pagination.
     * @return The list of the follows' user's info.
     */
    @ApiOperation(value = "Get the user's follows' info")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing or invalid request body")})
    @GetMapping("/{userId}/follows")
    public ResponseEntity<ResponseEnvelope<?>> getUserFollows(@PathVariable String userId, @RequestParam(required = false) Integer page ){
        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        PagedResult result = userService.getUserFollows(userId, page == null ? 1 : page, limit);
        return ResponseEntity.ok(ResponseEnvelope.ok(result));
    }

    /**
     * Update the info of the user.
     * It is used to update the user's info except the profile image of the user.
     *
     * @param userId        The id of the user.
     * @param userEditModel The UserDto object that contains the user's info needed to be updated.
     * @return The response of the edit user request that contains the user info; if there is no such user, return null
     */

    @ApiOperation(value = "Update the info of the user", notes = "Update the info of the user except of the profile of the user")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body")})
    @PutMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseEnvelope<?>> editUser(@PathVariable String userId, @RequestBody UserEditModel userEditModel) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEditModel, userDto);

        // update the user's status to the vip user; only upgrade to vip when the vipPass is not null.
        String vipPass = userEditModel.getVipPass();
        if (vipPass != null) {
            String vipVideoAccessQuestion = userEditModel.getVipVideoAccessQuestion();
            String vipVideoAccessAnswer = userEditModel.getVipVideoAccessAnswer();
            if(StringUtils.isBlank(vipVideoAccessAnswer) || StringUtils.isBlank(vipVideoAccessQuestion)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
            }
            // if the vipPass is correct, update the status of the user to vip
            if (userService.vipPassed(vipPass) && userService.isUserExists(userId)) {
                userDto.setStatus(UserStatus.VIP.getStatusCode());

                // create the vip folder for store the vip video
                userService.initFolder(userId, new String[]{FolderNameEnum.VIP.getFolderName()});

                //create the vip video access question and answer
                userDto.setVipVideoAccessQuestion(vipVideoAccessQuestion);
                userDto.setVipVideoAccessAnswer(vipVideoAccessAnswer);

            } else {
                // if the vipPass is incorrect, return a 403 response with an error message
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseEnvelope.error(-1, ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage()));
            }
        }
        // update the info into DB.

        int result = userService.editUser(userId, userDto, userEditModel.isDeleteVipVideoAccess());
        if (result == 0) {
            return ResponseEntity.ok().body(ResponseEnvelope.error(-1, ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage()));
        }
        return ResponseEntity.ok().body(ResponseEnvelope.ok(null));

    }

    /**
     * Upload the profile photo and return the relative path that stored in DB.
     *
     * @param userId The id of the user.
     * @param file   The profile photo.
     * @return The response of the upload user's profile request that contains the relative path of the profile.
     * @throws IOException
     */
    @ApiOperation(value = "Upload the profile photo of the user")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(value = "/{userId}/profile_photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelope<?>> changeProfilePhoto(@PathVariable String userId,
                                                                  @ApiParam(value = "Profile photo", required = true)
                                                                  @RequestPart MultipartFile file) throws IOException {
        // check if the userId is valid
        if (StringUtils.isBlank(userId) || file == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        } else if (!userService.isUserExists(userId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }


        // the dest path for store the upload profile photo
        String profileRelativePath = userService.getUserUploadFolder(userId, FolderNameEnum.PROFILE.getFolderName());

        // upload the file
        UploadResult result = userService.uploadFile(file, profileRelativePath);

        // error for upload
        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage()));
        }

//        String separator = FileSystems.getDefault().getSeparator();
        String separator = "/";

        // update the db
        String dbStoredPath = new StringBuilder().append(profileRelativePath)
                .append(separator)
                .append(result.getFileName()).toString();

        UserDto userDto = new UserDto();
        userDto.setProfilePhoto(dbStoredPath);
        userService.editUser(userId, userDto, false);

        return ResponseEntity.ok(ResponseEnvelope.ok(dbStoredPath));
    }


    /**
     * The login user follow or unFollow the video publisher.
     *
     * @param userId The id of the login user.
     * @param userRelationModel The model include the loginUserId publisherId and the status of the relationship.
     * @return
     */
    @ApiOperation(value = "Follow the user relationship API")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal server error")})
    @PutMapping(value = "/{userId}/follow_relationship")
    public ResponseEntity<ResponseEnvelope<?>> followOrUnFollower(@PathVariable String userId,
                                                                  @RequestBody UserRelationModel userRelationModel) {

        String publisherId = userRelationModel.getVideoPublishUserId();
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(publisherId)) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));

        }
        // follow the publisher
        if (userRelationModel.isLikeVideoOrFollowPublisher()) {
            if(!userService.isUserExists(userId) || !userService.isUserExists(publisherId)){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseEnvelope.error(-1, "UserId does not exists!"));
            }
            userService.addUserFollowRelation(userId, publisherId);
        } else {
            // unFollow the publisher
            userService.removeUserFollowRelation(userId, publisherId);
        }

        return ResponseEntity.ok(ResponseEnvelope.ok(null));

    }

}
