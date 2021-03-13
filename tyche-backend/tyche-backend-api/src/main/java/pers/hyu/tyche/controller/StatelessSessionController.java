package pers.hyu.tyche.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.enums.FolderNameEnum;
import pers.hyu.tyche.enums.UserStatus;
import pers.hyu.tyche.pojo.model.dto.UserDto;
import pers.hyu.tyche.pojo.model.request.UserRegisterAndLoginModel;
import pers.hyu.tyche.pojo.model.response.UserResponseModel;
import pers.hyu.tyche.service.UserService;
import pers.hyu.tyche.utils.EntityUtils;
import pers.hyu.tyche.utils.ResponseEnvelope;

/**
 * The stateless Session API.
 *
 * @author Heng Yu
 * @version 1.0
 */
@RestController
@RequestMapping("/sessions")
@Api(tags = "Stateless Session API")
public class StatelessSessionController {
    @Autowired
    private EntityUtils entityUtils;

    @Autowired
    private UserService userService;


    /**
     * Create a stateless session for the login user.
     *
     * @param userRegisterAndLoginModel The user's credential; include the username and password.
     * @return The login user's info.
     * @throws Exception
     */
    @ApiOperation(value = "Login", notes = "Use redis to store the stateless session for user login. Only need to provide the username and the password!")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Missing or invalid request body"),
            @ApiResponse(code = 401, message = "Username and password are not match."),
            @ApiResponse(code = 403, message = "The user's status is inactive; does not allowed login"),
    })
    @PostMapping
    public ResponseEntity<ResponseEnvelope<?>> login(@ApiParam(name = "user login model") @RequestBody UserRegisterAndLoginModel userRegisterAndLoginModel) throws Exception {
        // check the username and password can not be null
        if (StringUtils.isBlank(userRegisterAndLoginModel.getUsername()) || StringUtils.isBlank(userRegisterAndLoginModel.getPlainPassword())) {
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }


        // query the user from the db
        UserDto loginUserDto = userService.getUserForLogin(userRegisterAndLoginModel.getUsername(), userRegisterAndLoginModel.getPlainPassword());
        // username and password do not match return 401
        if (loginUserDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage()));
        }

        // user is inactive return 403
        if (loginUserDto.getStatus() == UserStatus.INACTIVE.getStatusCode()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseEnvelope.error(-1, ErrorMessages.FORBIDDEN.getErrorMessage()));
        }


        UserResponseModel loginUser = new UserResponseModel();
        BeanUtils.copyProperties(loginUserDto, loginUser);
        loginUser.setUserToken(userService.setLoginUserToken(loginUserDto.getId()));
        return ResponseEntity.ok(ResponseEnvelope.ok(loginUser));
    }

    /**
     * Delete the session when the user logout.
     * Remove the user token that stored in redis and delete the files in the user's temp folder.
     *
     * @param userId The user's id.
     * @return
     */
    @ApiOperation(value = "Logout", notes = "Delete the userToken from redis and delete the temp file when the user logout")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String",paramType = "header", required = true, example = "userId::userToken" )
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseEnvelope<?>> logout(@PathVariable String userId) {
        userService.deleteFolderOrFile(userId, FolderNameEnum.TEMP.getFolderName());
        userService.removeLoginUserToken(userId);
        return ResponseEntity.ok(null);

    }
}
