package pers.hyu.tyche.admin.mapper;

import org.springframework.stereotype.Repository;
import pers.hyu.tyche.admin.pojo.dto.UserReportDto;

import java.util.List;

@Repository
public interface UsersReportMapperCustomized {
    List<UserReportDto> selectAllReports();
}