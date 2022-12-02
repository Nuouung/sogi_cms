package cms.sogi_cms.cms.authority.service.impl;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.support.pagination.Paging;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
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
    public Long getTotalNumber(AuthoritySearch authoritySearch) {
        return null;
    }

    @Override
    public void updateAuthority(Long id, AuthorityCreateUpdateDto authorityDto) {

    }

    @Override
    public void deleteAuthority(Long id) {

    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceMap() {
        return null;
    }
}
