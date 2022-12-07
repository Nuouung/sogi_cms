package cms.sogi_cms.cms.login.controller;

import cms.sogi_cms.cms.support.SogiConstant;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH)
public class LoginAdminController {

    @GetMapping("/login")
    public String loginPageGet(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/login/login";
    }

    @PostMapping("/login")
    public String loginPost() {
        System.out.println("hello");
        return null;
    }

    @PostMapping("/logout")
    public String logoutPost() {
        return null;
    }
}
