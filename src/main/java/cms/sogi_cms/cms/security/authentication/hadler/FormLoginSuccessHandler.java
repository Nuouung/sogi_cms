package cms.sogi_cms.cms.security.authentication.hadler;

import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = userService.getUserByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
        user.removePassword();

        HttpSession session = request.getSession(true);

        session.setAttribute("currentUser", user);

        response.sendRedirect(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/main");
    }
}
