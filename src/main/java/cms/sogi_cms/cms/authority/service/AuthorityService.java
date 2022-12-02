package cms.sogi_cms.cms.authority.service;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.support.pagination.Paging;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

public interface AuthorityService {

    // c
    Long saveAuthority(AuthorityCreateUpdateDto authorityDto);

    // r
    Authority getAuthorityById(Long id);

    Authority getAuthorityByAuthorityName(String authorityName);

    Paging<Authority> getAuthorityList(AuthoritySearch authoritySearch);

    Long getTotalNumber(AuthoritySearch authoritySearch);

    // u
    void updateAuthority(Long id, AuthorityCreateUpdateDto authorityDto);

    // d
    void deleteAuthority(Long id);

    // 뭔지 정확히 모름 나중에 수정 필요
    LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceMap();
}
