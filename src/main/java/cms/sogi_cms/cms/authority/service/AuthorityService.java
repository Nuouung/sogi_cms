package cms.sogi_cms.cms.authority.service;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthorityResponseDto;
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

    Paging<AuthorityResponseDto> getAuthorityList(AuthoritySearch authoritySearch);

    Long getTotalNumber(AuthoritySearch authoritySearch);

    LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceMap();

    List<AuthorityResponseDto> getAllAuthorityList();

    // u
    void updateAuthority(Long id, AuthorityCreateUpdateDto authorityDto);

    // d
    void deleteAuthority(Long id);

    // utils
    AuthorityResponseDto toResponseDto(Authority authority);
}
