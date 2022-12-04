package cms.sogi_cms.cms.login.service.impl;

import cms.sogi_cms.cms.authority.entity.HttpMethod;
import cms.sogi_cms.cms.login.dto.LoginResultDto;
import cms.sogi_cms.cms.login.service.LoginService;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserService userService;

    @Override
    public LoginResultDto login(HttpServletRequest request, Authentication authentication) {
        if (request.getMethod().equals(HttpMethod.POST.toString())) {
            log.warn("[SOGICMS] 허용되지 않은 HTTP METHOD로의 인증 요청입니다. 요청된 METHOD: {}", request.getMethod());
            throw new AuthenticationServiceException("허용되지 않은 HTTP METHOD로의 인증 요청입니다. 요청된 METHOD: " + request.getMethod());
        }

        // filter 단에서 인증 결과로 반환된 authentication 객체에서 회원 객체를 꺼내 세션에 담는다.
        User user = (User) authentication.getPrincipal();
        HttpSession session = request.getSession();

        session.setAttribute("currentUser", user);

        // 회원의 최근 로그인 일자와 시간을 현재로 재조정한다.
        userService.updateLastLoginDateTime(user.getId());

        return new LoginResultDto(true, user.getUsername());
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
