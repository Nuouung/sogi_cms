package cms.sogi_cms.cms.security.authorization.metasource;

import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

    private final AuthorityService authorityService;

    private final List<String> whiteList = List.of(
            SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/login*");

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // Object = MethodInvocation
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        if (isInWhiteList(request)) {
            return null;
        }

        if (resourceMap == null) {
            resourceMap = authorityService.getResourceMap();
        }

        for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : resourceMap.entrySet()) {
            RequestMatcher requestMatcher = entry.getKey();
            if (requestMatcher.matches(request)) return entry.getValue();
        }

        return null;
    }

    private boolean isInWhiteList(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (String whiteURI : whiteList) {
            if (requestURI.matches(whiteURI)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null; // 사용 x
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class
                .isAssignableFrom(clazz);
    }

}
