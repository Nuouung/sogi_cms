package cms.sogi_cms.cms.role.service.impl;

import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class RoleServiceImplTest {

    @Autowired private RoleService roleService;

    @Test
    public void getDefaultUserRoleTest() {
        Role defaultUserRole = roleService.getDefaultUserRole();

        assertThat(defaultUserRole.getRoleName()).isEqualTo("ROLE_USER");
    }
}