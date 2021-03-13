package pers.hyu.tyche.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.hyu.tyche.dao.UsersReportMapper;
import pers.hyu.tyche.pojo.entity.UsersReport;
import pers.hyu.tyche.pojo.model.request.UserReportModel;
import pers.hyu.tyche.service.UserReportService;
import pers.hyu.tyche.utils.EntityUtils;

import java.util.Date;

/**
 * @see pers.hyu.tyche.service.UserReportService
 *
 * @author Heng Yu
 * @version 1.0
 */
@Service
public class UserReportServiceImpl implements UserReportService {

    @Autowired
    private EntityUtils entityUtils;

    @Autowired
    private UsersReportMapper usersReportMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addUserReport(UserReportModel userReportModel) {
        UsersReport usersReport = new UsersReport();
        BeanUtils.copyProperties(userReportModel, usersReport);
        usersReport.setId(entityUtils.generateId());
        usersReport.setCreateTime(new Date());
        usersReportMapper.insertSelective(usersReport);

    }

}
