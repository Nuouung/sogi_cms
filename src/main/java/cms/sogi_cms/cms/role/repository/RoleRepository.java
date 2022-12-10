package cms.sogi_cms.cms.role.repository;

import cms.sogi_cms.cms.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.isDefaultUser = true")
    Role getDefaultUserRole();

    @Query(nativeQuery = true,
            value = "SELECT * FROM sogi_role WHERE ID IN " +
                    "(SELECT ROLE_ID FROM sogi_role_authority_relation WHERE AUTHORITY_ID = " +
                    "(SELECT ID FROM sogi_authority WHERE AUTHORITY_NAME = :authorityName));")
    List<Role> getRoleListByAuthorityName(@Param("authorityName") String authorityName);

    Optional<Role> findByRoleName(String roleName);
}
