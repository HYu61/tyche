package pers.hyu.tyche.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.hyu.tyche.admin.pojo.Admins;
import pers.hyu.tyche.admin.service.AdminService;
import pers.hyu.tyche.utils.ResponseEnvelope;


import javax.servlet.http.HttpServletRequest;

/**
 * The controller is used for admin user login and logout.
 *
 * @author Heng Yu
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String directToLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEnvelope login(String username, String password, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseEnvelope.error(400, "Username or Password can not be null");
        }
        Admins loginAdmin = adminService.selectAdminForLogin(username, password);
        if (loginAdmin != null) {
            request.getSession().setAttribute("admin", loginAdmin);
            return ResponseEnvelope.ok(null);
        }
        return ResponseEnvelope.error(403,"Username or password is incorrect!");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return "login";
    }
}
