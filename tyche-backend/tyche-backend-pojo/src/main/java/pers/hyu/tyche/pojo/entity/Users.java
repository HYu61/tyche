package pers.hyu.tyche.pojo.entity;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table users
 */
public class Users {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     * Database Column Remarks:
     *   if the user does not upload the profile, provide one by default
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.profile_photo
     *
     * @mbg.generated
     */
    private String profilePhoto;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.nickname
     *
     * @mbg.generated
     */
    private String nickname;

    /**
     * Database Column Remarks:
     *   the num of how many followers this user has
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.follower_counts
     *
     * @mbg.generated
     */
    private Integer followerCounts;

    /**
     * Database Column Remarks:
     *   the num of people that the user have followed
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.follow_counts
     *
     * @mbg.generated
     */
    private Integer followCounts;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.received_like_counts
     *
     * @mbg.generated
     */
    private Integer receivedLikeCounts;

    /**
     * Database Column Remarks:
     *   the status of the user: -1、inactive, 0、active, 1 vip_user
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     * Database Column Remarks:
     *   the answer for reset password
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.security_answer
     *
     * @mbg.generated
     */
    private String securityAnswer;

    /**
     * Database Column Remarks:
     *   for the user provide the question for other user access this users vip video
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.vip_video_access_question
     *
     * @mbg.generated
     */
    private String vipVideoAccessQuestion;

    /**
     * Database Column Remarks:
     *   for other user access this users vip video
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.vip_video_access_answer
     *
     * @mbg.generated
     */
    private String vipVideoAccessAnswer;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.id
     *
     * @return the value of users.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.id
     *
     * @param id the value for users.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.username
     *
     * @return the value of users.username
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.username
     *
     * @param username the value for users.username
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.password
     *
     * @return the value of users.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.password
     *
     * @param password the value for users.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.profile_photo
     *
     * @return the value of users.profile_photo
     *
     * @mbg.generated
     */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.profile_photo
     *
     * @param profilePhoto the value for users.profile_photo
     *
     * @mbg.generated
     */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.nickname
     *
     * @return the value of users.nickname
     *
     * @mbg.generated
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.nickname
     *
     * @param nickname the value for users.nickname
     *
     * @mbg.generated
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.follower_counts
     *
     * @return the value of users.follower_counts
     *
     * @mbg.generated
     */
    public Integer getFollowerCounts() {
        return followerCounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.follower_counts
     *
     * @param followerCounts the value for users.follower_counts
     *
     * @mbg.generated
     */
    public void setFollowerCounts(Integer followerCounts) {
        this.followerCounts = followerCounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.follow_counts
     *
     * @return the value of users.follow_counts
     *
     * @mbg.generated
     */
    public Integer getFollowCounts() {
        return followCounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.follow_counts
     *
     * @param followCounts the value for users.follow_counts
     *
     * @mbg.generated
     */
    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.received_like_counts
     *
     * @return the value of users.received_like_counts
     *
     * @mbg.generated
     */
    public Integer getReceivedLikeCounts() {
        return receivedLikeCounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.received_like_counts
     *
     * @param receivedLikeCounts the value for users.received_like_counts
     *
     * @mbg.generated
     */
    public void setReceivedLikeCounts(Integer receivedLikeCounts) {
        this.receivedLikeCounts = receivedLikeCounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.status
     *
     * @return the value of users.status
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.status
     *
     * @param status the value for users.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.security_answer
     *
     * @return the value of users.security_answer
     *
     * @mbg.generated
     */
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.security_answer
     *
     * @param securityAnswer the value for users.security_answer
     *
     * @mbg.generated
     */
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.vip_video_access_question
     *
     * @return the value of users.vip_video_access_question
     *
     * @mbg.generated
     */
    public String getVipVideoAccessQuestion() {
        return vipVideoAccessQuestion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.vip_video_access_question
     *
     * @param vipVideoAccessQuestion the value for users.vip_video_access_question
     *
     * @mbg.generated
     */
    public void setVipVideoAccessQuestion(String vipVideoAccessQuestion) {
        this.vipVideoAccessQuestion = vipVideoAccessQuestion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.vip_video_access_answer
     *
     * @return the value of users.vip_video_access_answer
     *
     * @mbg.generated
     */
    public String getVipVideoAccessAnswer() {
        return vipVideoAccessAnswer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.vip_video_access_answer
     *
     * @param vipVideoAccessAnswer the value for users.vip_video_access_answer
     *
     * @mbg.generated
     */
    public void setVipVideoAccessAnswer(String vipVideoAccessAnswer) {
        this.vipVideoAccessAnswer = vipVideoAccessAnswer;
    }
}