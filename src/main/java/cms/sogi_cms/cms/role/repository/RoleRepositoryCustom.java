package cms.sogi_cms.cms.role.repository;

import cms.sogi_cms.cms.role.dto.RoleSearch;
import cms.sogi_cms.cms.role.entity.Role;

import java.util.List;

public interface RoleRepositoryCustom {

    List<Role> findList(RoleSearch roleSearch);
    Long count(RoleSearch roleSearch);
}
