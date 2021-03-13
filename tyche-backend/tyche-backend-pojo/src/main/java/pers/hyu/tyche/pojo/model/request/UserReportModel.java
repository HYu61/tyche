package pers.hyu.tyche.pojo.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "user report model",
        description = "The detail of the report, include the video publisher id, the video id, the title of the report and the detail")
public class UserReportModel {
    @ApiModelProperty(position = 1, required = true, example = "video publisher id")
    private String reportedUserId;

    @ApiModelProperty(position = 2, required = true, example = "video id")
    private String reportedVideoId;

    @ApiModelProperty(position = 3, required = true, example = "the report title")
    private String title;

    @ApiModelProperty(position = 4,  required = true, example = "detail")
    private String content;

    @ApiModelProperty(position = 5, required = true, example = "login user id")
    private String userId;

    public String getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(String reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public String getReportedVideoId() {
        return reportedVideoId;
    }

    public void setReportedVideoId(String reportedVideoId) {
        this.reportedVideoId = reportedVideoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
