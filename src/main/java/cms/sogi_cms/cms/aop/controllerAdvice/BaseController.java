package cms.sogi_cms.cms.aop.controllerAdvice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class BaseController {

//    @ModelAttribute
//    public void currentUser(Authentication authentication, Model model) {
//        model.addAttribute("authentication", authentication);
//    }
}
