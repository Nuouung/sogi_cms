package cms.sogi_cms.cms.user.service.impl;

import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.support.pagination.SortDirection;
import cms.sogi_cms.cms.user.dto.UserResponseDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void getAdminUserListTest() {
        Paging<UserResponseDto> adminUserPage = userService.getAdminUserList(new UserSearch(0, 10, null, null, null, null, null, null, null, null, null, null, null, null, null));
        Assertions.assertThat(adminUserPage.getContents().size()).isEqualTo(2);
    }
}