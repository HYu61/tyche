package pers.hyu.tyche.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

    @GetMapping("/center")
    public String center() {
        return "center";
    }

}



