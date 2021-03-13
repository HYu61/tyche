package pers.hyu.tyche.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.hyu.tyche.pojo.entity.Bgm;
import pers.hyu.tyche.service.BgmService;
import pers.hyu.tyche.utils.ResponseEnvelope;

import java.util.List;

/**
 * The BGM API
 *
 * @author Heng Yu
 * @version 1.0
 */
@RestController
@RequestMapping("/bgms")
@Api(tags = "Background music API")
public class BgmController {

    @Autowired
    private BgmService bgmService;


    /**
     * Get all the background music.
     *
     * @return A list of all the bgm.
     */
    @ApiOperation(value = "Get all the background music")
    @ApiImplicitParam(name = "Authorization", value = "User Token",
            dataType = "String",paramType = "header", required = true, example = "userId::userToken" )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content Found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelope<?>> getAllBgm(){
        List<Bgm> bgmList = bgmService.getAllBgm();
        if(bgmList.size() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ResponseEnvelope.ok(bgmList));

    }


}
