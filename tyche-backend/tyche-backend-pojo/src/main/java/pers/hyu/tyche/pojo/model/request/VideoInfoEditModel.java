package pers.hyu.tyche.pojo.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This is a model for editing the info of the video.
 *
 * @author Heng Yu
 */
@ApiModel(value = "video info edit model", description = "The info of the video is required for edit video service")
public class VideoInfoEditModel {

    @ApiModelProperty(dataType = "String", position = 1, allowEmptyValue = true)
    private String videoDesc;

    @ApiModelProperty(dataType = "boolean", position = 2, example = "0")
    private Boolean vipVideo;

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public Boolean getVipVideo() {
        return vipVideo;
    }

    public void setVipVideo(Boolean vipVideo) {
        this.vipVideo = vipVideo;
    }
}
