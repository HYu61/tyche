package pers.hyu.tyche.admin.pojo.dto;

import java.io.Serializable;
import java.util.Date;

public class UserReportDto implements Serializable {
    private static final long serialVersionUID = 2779915755477029155L;

    private String id;
    private String title;
    private String content;
    private Date createTime;

    private String reportedUsername;

    private String reportedVideoId;
    private String videoDesc;
    private String videoPath;
    private Integer status;

    private String submitUsername;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
        this.reportedUsername = reportedUsername;
    }

    public String getReportedVideoId() {
        return reportedVideoId;
    }

    public void setReportedVideoId(String reportedVideoId) {
        this.reportedVideoId = reportedVideoId;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubmitUsername() {
        return submitUsername;
    }

    public void setSubmitUsername(String submitUsername) {
        this.submitUsername = submitUsername;
    }
}
