package pers.hyu.tyche.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.hyu.tyche.admin.service.UserReportService;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.utils.ResponseEnvelope;

@Controller
@RequestMapping("/reports")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    @DeleteMapping("/{reportId}")
    @ResponseBody
    public ResponseEnvelope<?> removeReport(@PathVariable String reportId){
        if(StringUtils.isBlank(reportId)){
            return ResponseEnvelope.error(400, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        userReportService.deleteReport(reportId);
        return ResponseEnvelope.ok(null);
    }
}
