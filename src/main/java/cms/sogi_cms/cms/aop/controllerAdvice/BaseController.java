package cms.sogi_cms.cms.aop.controllerAdvice;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@ControllerAdvice
public class BaseController {

        @ModelAttribute
        public void bindCurrentUser(HttpServletRequest request, Model model) {
            HttpSession session = request.getSession(false);
            model.addAttribute("currentUser", session == null ? null : session.getAttribute("currentUser"));
        }
}
