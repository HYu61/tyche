package pers.hyu.tyche.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.hyu.tyche.admin.pojo.Users;
import pers.hyu.tyche.admin.service.UserService;
import pers.hyu.tyche.utils.PagedResult;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/showList")
    public String directToUserListPage(){
        return "users/usersList";
    }

    @GetMapping("/list")
    @ResponseBody
    public PagedResult getAllUsers(Users user, Integer page){
        PagedResult result = userService.selectUsers(user, page == null ? 1: page, 10);
        return result;
    }
}
