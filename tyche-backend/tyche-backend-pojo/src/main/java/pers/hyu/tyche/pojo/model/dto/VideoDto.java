package pers.hyu.tyche.pojo.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * This is a DTO for the video object.
 *
 * @author Heng Yu
 */
public class VideoDto implements Serializable {

    private static final long serialVersionUID = 1340590305416416998L;
    private String id;
    private String userId;
    private String bmgId;
    private String videoDesc;
    private String videoPath;
    private Float videoSeconds;
    private Integer videoWidth;
    private Integer videoHeight;
    private String coverPath;
    private Integer likeCounts;
    private Integer status;
    private Date createTime;
    private Boolean vipVideo;

    private String nickname;
    private String profilePhoto;

    private Boolean loginUserLikeTheVideo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBmgId() {
        return bmgId;
    }

    public void setBmgId(String bmgId) {
        this.bmgId = bmgId;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public Float getVideoSeconds() {
        return videoSeconds;
    }

    public void setVideoSeconds(Float videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public Integer getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Boolean getVipVideo() {
        return vipVideo;
    }

    public void setVipVideo(Boolean vipVideo) {
        this.vipVideo = vipVideo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Boolean getLoginUserLikeTheVideo() {
        return loginUserLikeTheVideo;
    }

    public void setLoginUserLikeTheVideo(Boolean loginUserLikeTheVideo) {
        this.loginUserLikeTheVideo = loginUserLikeTheVideo;
    }


}
