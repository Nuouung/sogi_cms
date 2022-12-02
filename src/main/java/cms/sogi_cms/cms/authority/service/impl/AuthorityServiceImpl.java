package cms.sogi_cms.cms.authority.service.impl;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.repository.AuthorityRepository;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import cms.sogi_cms.cms.support.pagination.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public Long saveAuthority(AuthorityCreateUpdateDto authorityDto) {
        return null;
    }

    @Override
    public Authority getAuthorityById(Long id) {
        return null;
    }

    @Override
    public Authority getAuthorityByAuthorityName(String authorityName) {
        return null;
    }

    @Override
    public Paging<Authority> getAuthorityList(AuthoritySearch authoritySearch) {
        return null;
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceMap() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap = new LinkedHashMap<>();
        authorityRepository.findAll()
                .forEach(authorityToResourceMap(resourceMap));

        return resourceMap;
    }

    private Consumer<Authority> authorityToResourceMap(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap) {
        return authority -> {
            ArrayList<ConfigAttribute> configAttributeList = new ArrayList<>();

            authority.getRoleAuthorityList()
                    .forEach(roleAuthority -> {
                        Role role = roleAuthority.getRole();
                        configAttributeList.add(new SecurityConfig(role.getRoleName()));
                    });

            resourceMap.put(
                    new AntPathRequestMatcher(authority.getUrlPath()),
                    configAttributeList);
        };
    }

    @Override
    public Long getTotalNumber(AuthoritySearch authoritySearch) {
        return null;
    }

    @Override
    public void updateAuthority(Long id, AuthorityCreateUpdateDto authorityDto) {

    }

    @Override
    public void deleteAuthority(Long id) {

    }
}
