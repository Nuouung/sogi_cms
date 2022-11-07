package cms.sogi_cms.cms.authority.service;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.support.pagination.Paging;

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
}
