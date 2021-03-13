package pers.hyu.tyche.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.pojo.entity.SearchRecords;
import pers.hyu.tyche.service.SearchRecordService;
import pers.hyu.tyche.utils.ResponseEnvelope;

/**
 * Hot Search API
 *
 * @author Heng Yu
 * @version 1.0
 */
@Api(tags = "Hot Search API")
@RestController
@RequestMapping("/search_content")
public class SearchRecordController {
    @Value("${default-hotSearch.topNum}")
    private int topNum;

    @Autowired
    SearchRecordService searchRecordService;


    /**
     * Get the top search content records.
     *
     * @return The set of the hot search.
     */
    @ApiOperation(value = "Get the hot search words")
    @GetMapping
    public ResponseEntity<ResponseEnvelope<?>> getHotSearchContentList(){
        // read records from redis
        return ResponseEntity.ok().body(ResponseEnvelope.ok(searchRecordService.getHotSearch(topNum)));
    }

    /**
     *Add the search content into redis.
     *
     * @param searchRecord The search content.
     * @return The request result.
     */
    @ApiOperation(value = "Add the search content to redis")
    @PostMapping
    public ResponseEntity<ResponseEnvelope<String>> addSearchContent(@ApiParam(allowEmptyValue = true)
                                                                         @RequestBody SearchRecords searchRecord ){
        String searchContent = searchRecord.getContent();
        if(StringUtils.isBlank(searchContent)){
            return ResponseEntity.badRequest().body(ResponseEnvelope.error(-1, ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage()));
        }

        searchRecordService.addSearchContent(searchContent);

        return ResponseEntity.ok().body(ResponseEnvelope.ok(null));
    }
}
