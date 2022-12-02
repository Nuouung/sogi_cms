package cms.sogi_cms.cms.security.authorization.metasource;

import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

public class UrlResourceMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

    private final AuthorityService authorityService;

    public UrlResourceMapFactoryBean(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
        if (resourceMap == null) init();
        return resourceMap;
    }

    private void init() {
        resourceMap = authorityService.getResourceMap();
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }
}
