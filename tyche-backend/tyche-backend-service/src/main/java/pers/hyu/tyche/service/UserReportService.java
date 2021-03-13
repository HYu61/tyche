package pers.hyu.tyche.service;

import pers.hyu.tyche.pojo.model.request.UserReportModel;

/**
 * The interface is the CRUD operations with user report video.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface UserReportService {
    /**
     * Add the report.
     *
     * @param userReportModel The model include the login user id, the reported video id, the publisher of the video and the detail of the problem.
     */
    void addUserReport(UserReportModel userReportModel);


}
