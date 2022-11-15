package cms.sogi_cms.cms.login.controller;

import cms.sogi_cms.cms.support.SogiConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginAdminController {

    @GetMapping("/login")
    public String hello() {
        System.out.println("hello");
        return null;
    }

    @PostMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/login")
    public String loginPost() {
        return null;
    }

    @PostMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/logout")
    public String logoutPost() {
        return null;
    }
}
