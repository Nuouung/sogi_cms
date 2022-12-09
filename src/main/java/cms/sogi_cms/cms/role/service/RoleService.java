package cms.sogi_cms.cms.role.service;

import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import cms.sogi_cms.cms.role.dto.RoleSearch;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.support.pagination.Paging;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

public interface RoleService {

    // c
    Long saveRole(RoleCreateUpdateDto roleDto);

    // r
    Role getRoleById(Long id);

    Role getRoleByRoleName(String roleName);

    Role getDefaultUserRole();

    List<Role> getRoleListContainCertainAuthority(String authorityName);

    Paging<Role> getRoleList(RoleSearch roleSearch);

    Long getTotalNumber(RoleSearch roleSearch);

    // u
    void updateRole(Long id, RoleCreateUpdateDto roleDto);

    // d
    void deleteRole(Long id);
}
