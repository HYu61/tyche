package pers.hyu.tyche.pojo.entity;

/**
 * Database Table Remarks:
 *   the relation table of the users who can access the publisher vip video
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table vip_video_access
 */
public class VipVideoAccess {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vip_video_access.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   the user posted the videos
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vip_video_access.publisher_id
     *
     * @mbg.generated
     */
    private String publisherId;

    /**
     * Database Column Remarks:
     *   the user who want to access the vip video
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vip_video_access.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vip_video_access.id
     *
     * @return the value of vip_video_access.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vip_video_access.id
     *
     * @param id the value for vip_video_access.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vip_video_access.publisher_id
     *
     * @return the value of vip_video_access.publisher_id
     *
     * @mbg.generated
     */
    public String getPublisherId() {
        return publisherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vip_video_access.publisher_id
     *
     * @param publisherId the value for vip_video_access.publisher_id
     *
     * @mbg.generated
     */
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vip_video_access.user_id
     *
     * @return the value of vip_video_access.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vip_video_access.user_id
     *
     * @param userId the value for vip_video_access.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}