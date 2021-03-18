package pers.hyu.tyche.admin.service;

import pers.hyu.tyche.admin.pojo.Users;
import pers.hyu.tyche.admin.pojo.dto.VideoDto;
import pers.hyu.tyche.utils.PagedResult;

public interface VideoService {
    PagedResult selectAllReportedVideos(Integer page, Integer limit);
    PagedResult selectAllVideos(VideoDto videoDto, Integer page, Integer limit);
    int updateVideoStatus(String videoId, Integer operation);
}
