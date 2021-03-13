package pers.hyu.tyche.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pers.hyu.tyche.pojo.entity.Videos;
import pers.hyu.tyche.pojo.entity.VideosExample;
import pers.hyu.tyche.pojo.model.dto.VideoDto;

import java.util.List;

@Repository
public interface VideosMapperCustomized {
    List<VideoDto> selectVideoCoversByUserId(String userId);
    List<VideoDto> selectVideoCovers(String searchContent);
    VideoDto selectVideoById(@Param("videoId") String videoId);
    void addLikeCounts(String videoId);
    void reduceLikeCounts(String videoId);
    List<VideoDto> selectUserLikedVideoCovers(String userId);
}