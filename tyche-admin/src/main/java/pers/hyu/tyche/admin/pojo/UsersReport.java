package pers.hyu.tyche.admin.pojo;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table users_report
 */
public class UsersReport {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users_report.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   the user who uploaded the video that may contain illegal content
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users_report.reported_user_id
     *
     * @mbg.generated
     */
    private String reportedUserId;

    /**
     * Database Column Remarks:
     *   the video that contains illegal content
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users_report.reported_video_id
     *
     * @mbg.generated
     */
    private String reportedVideoId;

    /**
     * Database Column Remarks:
     *   the title of the illegal type; the user will choose from the enum
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users_report.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     * Database Column Remarks:
     *   the detail of the report
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users_report.content
     *
     * @mbg.generated
     */
    private String content;

    /**
     * Database Column Remarks:
     *   the user who report this illegal video
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users_report.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users_report.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users_report.id
     *
     * @return the value of users_report.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users_report.id
     *
     * @param id the value for users_report.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users_report.reported_user_id
     *
     * @return the value of users_report.reported_user_id
     *
     * @mbg.generated
     */
    public String getReportedUserId() {
        return reportedUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users_report.reported_user_id
     *
     * @param reportedUserId the value for users_report.reported_user_id
     *
     * @mbg.generated
     */
    public void setReportedUserId(String reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users_report.reported_video_id
     *
     * @return the value of users_report.reported_video_id
     *
     * @mbg.generated
     */
    public String getReportedVideoId() {
        return reportedVideoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users_report.reported_video_id
     *
     * @param reportedVideoId the value for users_report.reported_video_id
     *
     * @mbg.generated
     */
    public void setReportedVideoId(String reportedVideoId) {
        this.reportedVideoId = reportedVideoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users_report.title
     *
     * @return the value of users_report.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users_report.title
     *
     * @param title the value for users_report.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users_report.content
     *
     * @return the value of users_report.content
     *
     * @mbg.generated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users_report.content
     *
     * @param content the value for users_report.content
     *
     * @mbg.generated
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users_report.user_id
     *
     * @return the value of users_report.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users_report.user_id
     *
     * @param userId the value for users_report.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users_report.create_time
     *
     * @return the value of users_report.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users_report.create_time
     *
     * @param createTime the value for users_report.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}