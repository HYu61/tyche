package pers.hyu.tyche.pojo.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This is a model for get basic info of the video.
 *
 * @author Heng Yu
 */
@ApiModel(value = "video info model", description = "The info of the video is required for upload video service")
public class VideoInfoModel {
    @ApiModelProperty(dataType = "String", position = 1, required = true)
    private String userId;

    @ApiModelProperty(dataType = "float", position = 2, required = true)
    private Float videoSeconds;

    @ApiModelProperty(dataType = "int", position = 3, required = true)
    private Integer videoWidth;

    @ApiModelProperty(dataType = "int", position = 4, required = true)
    private Integer videoHeight;

    @ApiModelProperty(dataType = "String", position = 5, allowEmptyValue = true)
    private String bgmId;

    @ApiModelProperty(dataType = "String", position = 6, allowEmptyValue = true)
    private String videoDesc;

    @ApiModelProperty(dataType = "boolean", position = 7, example = "0")
    private Boolean vipVideo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getVipVideo() {
        return vipVideo;
    }

    public void setVipVideo(Boolean vipVideo) {
        this.vipVideo = vipVideo;
    }

    public String getBgmId() {
        return bgmId;
    }

    public void setBgmId(String bgmId) {
        this.bgmId = bgmId;
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


}
