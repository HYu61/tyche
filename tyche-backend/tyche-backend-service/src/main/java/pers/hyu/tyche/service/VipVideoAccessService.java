package pers.hyu.tyche.service;

/**
 * The interface is used for CRUD the login user if can access the vip video.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface VipVideoAccessService {
    /**
     * Add the vip video access relationship between the publisher and the login user.
     * After added the relationship, the login user can access the publisher's all vip videos.
     *
     * @param publisherId
     * @param userId
     * @param accessAnswer
     * @return -1 means the answer is incorrect, 1 means the relationship is added.
     */
    int addUserAccess(String publisherId, String userId, String accessAnswer);

    /**
     * Check if the login user can access the publisher's vip video.
     * The vip user can access all vip videos; only the login user answered the access question that can access the publisher's all vip videos.
     *
     * @param publisherId
     * @param userId
     * @return true the login user can access the vip video; otherwise can not.
     */
    boolean isUserAccessible(String publisherId, String userId);

    /**
     * Delete all the relationship that the users can access the publisher's vip video.
     *
     * @param publisherId The id of the video publisher.
     * @return
     */
    int removeUserAccess(String publisherId);
}
