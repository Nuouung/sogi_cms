package cms.sogi_cms.cms.user.service;

import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    // c
    Long saveUser(UserCreateUpdateDto userDto);

    // r
    User getUserById(Long id);

    User getUserByUsername(String username);

    Page<User> getUserList(UserSearch userSearch);

    Integer count(UserSearch userSearch);

    // u
    void updateUser(Long id, UserCreateUpdateDto userDto);

    void updatePassword(Long id, String newPassword);

//    void updateProfile(Long id, ...);

    void updateLastLoginDateTime(Long id);

    // d
    void deleteUser(Long id);

    void deleteUsers(List<Long> idList);
}
