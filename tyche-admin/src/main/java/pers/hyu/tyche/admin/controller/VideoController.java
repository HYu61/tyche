package pers.hyu.tyche.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.hyu.tyche.admin.pojo.dto.VideoDto;
import pers.hyu.tyche.admin.service.VideoService;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.utils.PagedResult;
import pers.hyu.tyche.utils.ResponseEnvelope;

@Controller
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping("/showReportList")
    public String showReportedVideoList(){
        return "video/reportList";
    }

    @GetMapping("/showVideoList")
    public String showVideoList(){
        return "video/videoList";
    }

    @GetMapping("/reportedVideoList")
    @ResponseBody
    public PagedResult getAllReportedVideo(Integer page){
        PagedResult result = videoService.selectAllReportedVideos(page == null ? 1: page, 10);
        return result;
    }

    @GetMapping("/videoList")
    @ResponseBody
    public PagedResult getAllVideo(VideoDto videoDto, Integer page){
        PagedResult result = videoService.selectAllVideos(videoDto, page == null ? 1: page, 10);
        return result;
    }

    @PatchMapping("/{videoId}")
    @ResponseBody
    public ResponseEnvelope<?> inactiveVideo(@PathVariable String videoId,
                                             @RequestParam Integer operation){

        int result = 0;
        if(StringUtils.isBlank(videoId) || operation == null){
            return ResponseEnvelope.error(400, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        else {
            result = videoService.updateVideoStatus(videoId, operation);
        }

        return result > 0 ? ResponseEnvelope.ok(null) : ResponseEnvelope.error(500, ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());
    }
}
