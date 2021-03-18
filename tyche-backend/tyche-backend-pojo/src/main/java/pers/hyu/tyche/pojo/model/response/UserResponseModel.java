package pers.hyu.tyche.pojo.model.response;

import io.swagger.annotations.ApiModel;

/**
 * This is a model for response of getting the user's info.
 *
 * @author Heng Yu
 */
@ApiModel(value = "User response model", description = "Response for the user details")
public class UserResponseModel {
    private String id;

    private String userToken;     // used in redis for login  session


    private String username;
    private String profilePhoto;
    private String nickname;
    private Integer followerCounts;
    private Integer followCounts;
    private Integer receivedLikeCounts;
    private Integer status;
    private Boolean hasFollowed;
    private String vipVideoAccessQuestion;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getFollowerCounts() {
        return followerCounts;
    }

    public void setFollowerCounts(Integer followerCounts) {
        this.followerCounts = followerCounts;
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getReceivedLikeCounts() {
        return receivedLikeCounts;
    }

    public void setReceivedLikeCounts(Integer receivedLikeCounts) {
        this.receivedLikeCounts = receivedLikeCounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getHasFollowed() {
        return hasFollowed;
    }

    public void setHasFollowed(Boolean hasFollowed) {
        this.hasFollowed = hasFollowed;
    }

    public String getVipVideoAccessQuestion() {
        return vipVideoAccessQuestion;
    }

    public void setVipVideoAccessQuestion(String vipVideoAccessQuestion) {
        this.vipVideoAccessQuestion = vipVideoAccessQuestion;
    }
}
