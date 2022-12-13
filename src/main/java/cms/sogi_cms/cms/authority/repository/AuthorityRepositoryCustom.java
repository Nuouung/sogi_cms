package cms.sogi_cms.cms.authority.repository;

import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;

import java.util.List;

public interface AuthorityRepositoryCustom {

    List<Authority> findList(AuthoritySearch authoritySearch);
    Long count(AuthoritySearch authoritySearch);
}
