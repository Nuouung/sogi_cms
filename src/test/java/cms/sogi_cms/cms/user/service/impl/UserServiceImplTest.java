package cms.sogi_cms.cms.user.service.impl;

import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired private UserService userService;

    @Test
    void saveUser() {
        UserCreateUpdateDto dto = new UserCreateUpdateDto();
        dto.setUsername("jinseok");
        dto.setPassword("1234");
        dto.setLastname("Lee");
        dto.setFirstname("Jinseok");
        dto.setEmail("jin6016@gmail.com");
        dto.setMailing(true);
        dto.setPhoneNumber("010-1111-2222");
        dto.setGender("M");
        dto.setBirthday("1994-06-16");
        dto.setBirthdaySolar(true);
        dto.setAddress("양주시");
        dto.setAddressSub("덕게동");
        dto.setZipCode("11111");

        userService.saveUser(dto);
    }

    @Test
    void getUserByUsername() {
        UserCreateUpdateDto dto = new UserCreateUpdateDto();
        dto.setUsername("jinseok");
        dto.setPassword("1234");
        dto.setLastname("Lee");
        dto.setFirstname("Jinseok");
        dto.setEmail("jin6016@gmail.com");
        dto.setMailing(true);
        dto.setPhoneNumber("010-1111-2222");
        dto.setGender("M");
        dto.setBirthday("1994-06-16");
        dto.setBirthdaySolar(true);
        dto.setAddress("양주시");
        dto.setAddressSub("덕게동");
        dto.setZipCode("11111");

        userService.saveUser(dto);

        User user = userService.getUserByUsername("jinseok");
        Assertions.assertThat(user.getUsername()).isEqualTo("jinseok");
    }

    @Test
    void getUserList() {
        UserCreateUpdateDto dto = new UserCreateUpdateDto();
        dto.setUsername("jinseok");
        dto.setPassword("1234");
        dto.setLastname("Lee");
        dto.setFirstname("Jinseok");
        dto.setEmail("jin6016@gmail.com");
        dto.setMailing(true);
        dto.setPhoneNumber("010-1111-2222");
        dto.setGender("M");
        dto.setBirthday("1994-06-16");
        dto.setBirthdaySolar(true);
        dto.setAddress("양주시");
        dto.setAddressSub("덕게동");
        dto.setZipCode("11111");

        userService.saveUser(dto);
        userService.saveUser(dto);
        userService.saveUser(dto);
        userService.saveUser(dto);
        userService.saveUser(dto);
        userService.saveUser(dto);
        userService.saveUser(dto);

        UserSearch userSearch = new UserSearch(0, 5, null, null, true, null, null, null, null, null, null, true, null, true, true, false);

        Paging<User> paging = userService.getUserList(userSearch);
        Assertions.assertThat(paging.getTotal()).isEqualTo(7);
        Assertions.assertThat(paging.getContents().size()).isEqualTo(5);
    }

    @Test
    void updateUser() {
        UserCreateUpdateDto dto = new UserCreateUpdateDto();
        dto.setUsername("jinseok");
        dto.setPassword("1234");
        dto.setLastname("Lee");
        dto.setFirstname("Jinseok");
        dto.setEmail("jin6016@gmail.com");
        dto.setMailing(true);
        dto.setPhoneNumber("010-1111-2222");
        dto.setGender("M");
        dto.setBirthday("1994-06-16");
        dto.setBirthdaySolar(true);
        dto.setAddress("양주시");
        dto.setAddressSub("덕게동");
        dto.setZipCode("11111");

        userService.saveUser(dto);
        User foundUser = userService.getUserByUsername("jinseok");
        dto.setGender("F");

        userService.updateUser(foundUser.getId(), dto);

        Assertions.assertThat(userService.getUserByUsername("jinseok").getGender()).isEqualTo("F");
    }

    @Test
    void updatePassword() {
        UserCreateUpdateDto dto = new UserCreateUpdateDto();
        dto.setUsername("jinseok");
        dto.setPassword("1234");
        dto.setLastname("Lee");
        dto.setFirstname("Jinseok");
        dto.setEmail("jin6016@gmail.com");
        dto.setMailing(true);
        dto.setPhoneNumber("010-1111-2222");
        dto.setGender("M");
        dto.setBirthday("1994-06-16");
        dto.setBirthdaySolar(true);
        dto.setAddress("양주시");
        dto.setAddressSub("덕게동");
        dto.setZipCode("11111");

        userService.saveUser(dto);
        User foundUser = userService.getUserByUsername("jinseok");

        userService.updatePassword(foundUser.getId(), "12345");

        userService.getUserByUsername()
    }

    @Test
    void updateLastLoginDateTime() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void deleteUsers() {
    }
}