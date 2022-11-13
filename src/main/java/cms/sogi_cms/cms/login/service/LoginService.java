package cms.sogi_cms.cms.login.service;

import cms.sogi_cms.cms.login.dto.LoginResultDto;
import cms.sogi_cms.cms.user.entity.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    LoginResultDto login(HttpServletRequest request, Authentication authentication);
    void logout(HttpServletRequest request);
}
