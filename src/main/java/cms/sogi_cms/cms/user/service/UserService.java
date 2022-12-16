package cms.sogi_cms.cms.user.service;

import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserResponseDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    // c
    Long saveUser(UserCreateUpdateDto userDto) throws IOException;

    // r
    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    Paging<UserResponseDto> getAdminUserList(UserSearch userSearch);

    Paging<UserResponseDto> getNonAminUserList(UserSearch userSearch);

    Paging<UserResponseDto> getUserList(UserSearch userSearch);

    Long getTotalNumber(UserSearch userSearch);

    // u
    void updateUser(Long id, UserCreateUpdateDto userDto) throws IOException;

    void updatePassword(Long id, String newPassword);

//    void updateProfile(Long id, ...);

    void updateLastLoginDateTime(Long id);

    // d
    void deleteUser(Long id);

    void deleteUsers(List<Long> idList);

    // utils
    UserResponseDto toResponseDto(User user);

}
