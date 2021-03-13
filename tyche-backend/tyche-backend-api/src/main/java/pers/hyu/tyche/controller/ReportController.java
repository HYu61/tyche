package pers.hyu.tyche.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.pojo.model.request.UserReportModel;
import pers.hyu.tyche.service.UserReportService;
import pers.hyu.tyche.service.UserService;
import pers.hyu.tyche.service.VideoService;
import pers.hyu.tyche.utils.EntityUtils;
import pers.hyu.tyche.utils.ResponseEnvelope;

/**
 * Report the illegal video API.
 *
 * @author Heng Yu
 * @version 1.0
 */
@RestController
@RequestMapping("/illegal_video_reports")
@Api(tags = "Report the illegal video API")
public class ReportController {
    @Autowired
    private EntityUtils entityUtils;

    @Autowired
    private UserReportService userReportService;

    @ApiOperation(value = "Report the illegal video.")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping
    public ResponseEntity<ResponseEnvelope<?>> reportIllegalVideo(@RequestBody UserReportModel userReportModel) throws IllegalAccessException {
        if(entityUtils.isNotAllFieldSet(userReportModel,false)){
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        userReportService.addUserReport(userReportModel);
        return ResponseEntity.ok().body(ResponseEnvelope.ok("Thanks for reporting!"));
    }

}
