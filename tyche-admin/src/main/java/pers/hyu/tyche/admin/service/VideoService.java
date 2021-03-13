package pers.hyu.tyche.admin.service;

import pers.hyu.tyche.admin.pojo.Users;
import pers.hyu.tyche.utils.PagedResult;

public interface VideoService {
    PagedResult selectAllReportedVideos(Integer page, Integer limit);
    int updateVideoStatus(String videoId, Integer operation);
}
