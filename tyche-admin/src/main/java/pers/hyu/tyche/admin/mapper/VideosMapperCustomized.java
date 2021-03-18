package pers.hyu.tyche.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.hyu.tyche.admin.pojo.Videos;
import pers.hyu.tyche.admin.pojo.VideosExample;
import pers.hyu.tyche.admin.pojo.dto.UserReportDto;
import pers.hyu.tyche.admin.pojo.dto.VideoDto;

import java.util.List;

@Repository
public interface VideosMapperCustomized {
    List<VideoDto> selectAllVideo(@Param("username") String username, @Param("status") Integer status);
}