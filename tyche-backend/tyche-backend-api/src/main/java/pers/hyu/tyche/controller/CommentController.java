package pers.hyu.tyche.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.pojo.model.request.CommentModel;
import pers.hyu.tyche.service.CommentService;
import pers.hyu.tyche.utils.EntityUtils;
import pers.hyu.tyche.utils.PagedResult;
import pers.hyu.tyche.utils.ResponseEnvelope;

@RestController
@RequestMapping("/comments")
@Api(tags = "Comments API" )
public class CommentController {

    @Value("${pagehelper.comments-limit}")
    private Integer limit;

    @Autowired
    private EntityUtils entityUtils;

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Add the comment under the video.")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String", paramType = "header", required = true, example = "userId::userToken")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing or invalid request body")})
    @PostMapping
    public ResponseEntity<ResponseEnvelope<?>> addVideoComment(@RequestBody CommentModel commentModel) throws IllegalAccessException {
        if(entityUtils.isNotAllFieldSet(commentModel, true)){
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));

        }
        commentService.addComment(commentModel);

        return ResponseEntity.ok(ResponseEnvelope.ok(null));
    }

    @ApiOperation(value = "Get all the comment to the video.")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Missing or invalid request body")})
    @GetMapping(value = "/videos/{videoId}")
    public ResponseEntity<ResponseEnvelope<?>> getVideoComments(@PathVariable String videoId,
                                                                @RequestParam(required = false) Integer page){
        if(StringUtils.isBlank(videoId)){
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));

        }

        // get all the comments 
        PagedResult list = commentService.getAllComments(videoId, page == null ? 1: page, limit);
        return ResponseEntity.ok(ResponseEnvelope.ok(list));
    }
}
