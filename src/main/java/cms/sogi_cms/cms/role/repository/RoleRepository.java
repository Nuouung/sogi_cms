package cms.sogi_cms.cms.role.repository;

import cms.sogi_cms.cms.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.isDefaultUser = true")
    Role getDefaultUserRole();

    Optional<Role> findByRoleName(String roleName);
}
