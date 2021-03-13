package pers.hyu.tyche.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.hyu.tyche.admin.mapper.UsersReportMapper;
import pers.hyu.tyche.admin.service.UserReportService;

@Service
public class UserReportServiceImpl implements UserReportService {

    @Autowired
    private UsersReportMapper usersReportMapper;

    @Override
    public void deleteReport(String reportId) {
        usersReportMapper.deleteByPrimaryKey(reportId);
    }
}
