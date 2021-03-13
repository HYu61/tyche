package pers.hyu.tyche.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.hyu.tyche.admin.pojo.Bgm;
import pers.hyu.tyche.admin.service.BgmService;
import pers.hyu.tyche.utils.PagedResult;
import pers.hyu.tyche.utils.ResponseEnvelope;
import pers.hyu.tyche.utils.UploadResult;

import java.io.File;
import java.io.IOException;

/**
 * The bgm controller is used for the Bgms CRUD.
 *
 * @author Heng Yu
 * @version 1.0
 */
@Controller
@RequestMapping("/bgms")
public class BgmController {
//


    @Autowired
    private BgmService bgmService;


    /**
     * Navigate the add bgm page.
     *
     * @return The path of the page.
     */
    @GetMapping("/showAddBgm")
    public String goToBgmAddingPage() {
        return "bgm/addBgm";
    }

    @PostMapping("/bgmUpload")
    @ResponseBody
    public ResponseEnvelope bgmUpLoad(MultipartFile file) throws IOException {
        String dest = File.separator+"bgms";

        UploadResult result = bgmService.uploadBgm(file,dest );
        if (result == null) {
            return ResponseEnvelope.error(-1, "BGM upload failed");
        }

        Bgm bgm = new Bgm();
        String bgmDBPath = dest + File.separator + result.getFileName();


        bgm.setPath(bgmDBPath);
        return ResponseEnvelope.ok(bgmDBPath);
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public ResponseEnvelope addBgm(Bgm bgm){
        if(bgm == null){
            return ResponseEnvelope.error(-1, "Upload failed!");
        }

        bgmService.addBgm(bgm);
        return ResponseEnvelope.ok(null);
    }
//
    @GetMapping("/showBgmList")
    public String showBgmList(){
        return "bgm/bgmList";
    }
//
    @GetMapping("/getBgmList")
    @ResponseBody
    public PagedResult getBgmList(Integer page){
        return bgmService.selectAllBgm(page, 10);
    }
//
    @DeleteMapping("/delBgm")
    @ResponseBody
    public ResponseEnvelope delBgm(String bgmId){

        if(StringUtils.isBlank(bgmId)){
            return ResponseEnvelope.error(-1,"BGM id is required!");
        }

        bgmService.deleteBgmById(bgmId);
        return ResponseEnvelope.ok(null);

    }

}
