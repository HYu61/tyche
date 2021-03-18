package pers.hyu.tyche.pojo.model.dto;

import java.io.Serializable;

/**
 * This is a DTO for the user object.
 *
 * @author Heng Yu
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID = 4821438788040900898L;

    private String id;
    private String username;
    private String plainPassword;
    private String password; // encrypted password
    private String profilePhoto;
    private String nickname;
    private Integer followerCounts;
    private Integer followCounts;
    private Integer receivedLikeCounts;

    //the status of the user: -1、inactive, 0、active, 1 vip_user
    private Integer status;
    private String securityAnswer;
    private String vipVideoAccessQuestion;
    private String vipVideoAccessAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getVipVideoAccessQuestion() {
        return vipVideoAccessQuestion;
    }

    public void setVipVideoAccessQuestion(String vipVideoAccessQuestion) {
        this.vipVideoAccessQuestion = vipVideoAccessQuestion;
    }

    public String getVipVideoAccessAnswer() {
        return vipVideoAccessAnswer;
    }

    public void setVipVideoAccessAnswer(String vipVideoAccessAnswer) {
        this.vipVideoAccessAnswer = vipVideoAccessAnswer;
    }
}
