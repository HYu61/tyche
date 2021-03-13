package pers.hyu.tyche.service;

import pers.hyu.tyche.pojo.model.dto.UserDto;
import pers.hyu.tyche.utils.PagedResult;


/**
 * The interface is the CRUD operations for the user entity.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface UserService extends UploadFileService {

    /**
     * Check if the user exists.
     *
     * @param usernameOrId The username or the Id of the user.
     * @return A boolean result. If the username already exists in the DB return true; otherwise, return false.
     */
    boolean isUserExists(String usernameOrId);

    /**
     * Register a new user.
     *
     * @param userDto The new user's info for registering.
     * @return A String result. The userId of the new user.
     */
    String registerUser(UserDto userDto) throws Exception;


    /**
     * Reset the user's password.
     * The user's username and security answer are used for verify the users
     *
     * @param username       The user's username.
     * @param securityAnswer The security answer.
     * @param newPassword    The new password.
     * @return True if reset successful; otherwise return false.
     * @throws Exception
     */
    boolean resetPassword(String username, String securityAnswer, String newPassword) throws Exception;


    /**
     * Get the user's info for login.
     *
     * @param username      The user's username.
     * @param plainPassword The user's password.
     * @return If the user's username and password are correct, return the user's info; otherwise return null;
     * @throws Exception
     */
    UserDto getUserForLogin(String username, String plainPassword) throws Exception;


    /**
     * Set and store a unique token for the user login.
     * The token will expired in the specific time.
     *
     * @param userId The userId of the login user.
     * @return The token of the login user.
     */
    String setLoginUserToken(String userId);


    /**
     * Remove the userToken from redis.
     *
     * @param userId The userId of the login user.
     */
    void removeLoginUserToken(String userId);


    /**
     * Get the user info by id.
     *
     * @param userId The id of the user.
     * @return The UserDto object that contains the user's info.
     */
    UserDto getUserById(String userId);

    /**
     * Edit the user's information.
     *
     * @param userId  The id of the user.
     * @param userDto The UserDto object that contains the user's info needed to be updated.
     * @return The num of the successful updated records.
     */
    int editUser(String userId, UserDto userDto);

    /**
     * The login user follow the video publisher.
     *
     * @param loginUserId The id of the login user.
     * @param publisherId The id of the video publisher.
     */
    void addUserFollowRelation(String loginUserId, String publisherId);

    /**
     * The login user unFollow the video publisher.
     *
     * @param loginUserId The id of the login user.
     * @param publisherId The id of the video publisher.
     */
    void removeUserFollowRelation(String loginUserId, String publisherId);

    /**
     * Get if the login user follow the video publisher.
     *
     * @param loginUserId The id of the login user.
     * @param publisherId The id of the video publisher.
     * @return True if the login user has followed the video publisher; otherwise return false.
     */
    boolean isFollowed(String loginUserId, String publisherId);


    /**
     * Check if the answer of the vip-upgrade question is correct.
     *
     * @param vipPass The answer of the vip-upgrade question.
     * @return A boolean result. If the answer is correct return true; otherwise return false.
     */
    boolean vipPassed(String vipPass);

    /**
     * Get the relative folder path according to the userId and the name of the folder.
     *
     * @param userId     The id of the user.
     * @param folderName The name of the folder that need to be get.
     * @return The relative folder path.
     */
    String getUserUploadFolder(String userId, String folderName);


    /**
     * Get all the user's follows' info.
     *
     * @param userId The id of the user.
     * @param page   The num of from which page to start select the data.
     * @param limit  The num of how many records will display in one page.
     * @return The pagination of the list of user's follows.
     */
    PagedResult getUserFollows(String userId, Integer page, Integer limit);


}
