package cms.sogi_cms.cms.user.service.impl;

import cms.sogi_cms.SogiCmsApplication;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired private UserService userService;

    @BeforeEach
    void beforeEach() {
        saveUser();
    }

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
        User user = userService.getUserByUsername("jinseok");
        Assertions.assertThat(user.getUsername()).isEqualTo("jinseok");
    }

    @Test
    void getUserList() {
        userService.getUserList(null);
    }

    @Test
    void getTotalUserNumber() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updatePassword() {
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