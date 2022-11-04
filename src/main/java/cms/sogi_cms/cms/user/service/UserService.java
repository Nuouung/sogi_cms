package cms.sogi_cms.cms.user.service;

import cms.sogi_cms.cms.user.dto.UserDto;
import cms.sogi_cms.cms.user.entity.User;

import java.util.List;

public interface UserService {

    // c
    Long saveUser(UserDto userDto);

    // r
    User getUserById(Long id);
    User getUserByUsername(String username);
//    ... getUserList(...);
//    Integer getTotalUserNumber(...);

    // u
    void updateUser(Long id, UserDto userDto);
    void updatePassword(Long id, String newPassword);
//    void updateProfile(Long id, ...);
    void updateLastLoginDateTime(String username);

    // d
    void deleteUser(Long id);
    void deleteUsers(List<Long> idList);
}
