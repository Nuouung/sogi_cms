package cms.sogi_cms.cms.user.service.impl;

import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.file.service.FileService;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.service.RoleService;
import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserResponseDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.repository.UserRepository;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;
    private final FileService fileService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Long saveUser(UserCreateUpdateDto userDto) throws IOException {
        // 비밀번호 암호화
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        // 부여할 역할 조회
        Role role = getRole(userDto);

        // 객체 생성 및 저장
        User user = User.create(userDto, hashedPassword, role);

        // 프로필 사진 저장
        if (userDto.getProfilePicture() != null && !userDto.getProfilePicture().isEmpty()) {
            setFile(userDto, user);
        }

        userRepository.save(user);

        return user.getId();
    }

    private Role getRole(UserCreateUpdateDto userDto) {
        if (StringUtils.hasText(userDto.getRoleName())) {
            return roleService.getRoleByRoleName(userDto.getRoleName());
        }

        return roleService.getDefaultUserRole();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Paging<UserResponseDto> getAdminUserList(UserSearch userSearch) {
        userSearch.setRoleList(roleService.getRoleListContainCertainAuthority("admin-access"));

        return getUserList(userSearch);
    }

    @Override
    public Paging<UserResponseDto> getNonAminUserList(UserSearch userSearch) {
        userSearch.setRoleList(List.of(roleService.getDefaultUserRole()));

        return getUserList(userSearch);
    }

    @Override
    public Paging<UserResponseDto> getUserList(UserSearch userSearch) {
        List<UserResponseDto> contents = userRepository.findList(userSearch).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        Long total = userRepository.count(userSearch);

        return new Paging<>(contents, total, userSearch);
    }

    @Override
    public UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setLastname(user.getLastname());
        dto.setFirstname(user.getFirstname());
        dto.setEmail(user.getEmail());
        dto.setIsMailing(user.getIsMailing());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender());
        dto.setBirthday(user.getBirthday());
        dto.setIsBirthdaySolar(user.getIsBirthdaySolar());
        dto.setRoadNameAddress(user.getAddress().getRoadNameAddress());
        dto.setLotNumberAddress(user.getAddress().getLotNumberAddress());
        dto.setDetailAddress(user.getAddress().getDetailAddress());
        dto.setZipCode(user.getAddress().getZipCode());
        dto.setExtraAddress(user.getAddress().getExtraAddress());
        dto.setActive(user.isActive());
        dto.setDeleted(user.isDeleted());
        dto.setRegisteredDateTime(user.getRegisteredDateTime());
        dto.setPasswordLastUpdatedDateTime(user.getPasswordLastUpdatedDateTime());
        dto.setLastLoginDateTime(user.getLastLoginDateTime());
        dto.setRoleKoreanName(user.getRole().getKoreanName());
        dto.setFile(user.getFile() == null ? null : fileService.toResponseDto(user.getFile()));
        dto.setRoleName(user.getRole().getRoleName());

        return dto;
    }

    @Override
    public Long getTotalNumber(UserSearch userSearch) {
        return userRepository.count(userSearch);
    }

    @Override
    public void updateUser(Long id, UserCreateUpdateDto userDto) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        // 역할을 조회한다. (update에서는 userDto에 roleName이 빈 값일 수 없다. 왜냐하면 업데이트 하려고 하는 회원은 무조건 역할을 가지고 있어야 하기 때문이다)
        // "" 체크는 검증단에서 이미 진행함.
        Role role = getRole(userDto);

        if (userDto.getProfilePicture() != null && !userDto.getProfilePicture().isEmpty()) {
            setFile(userDto, user);
        }

        user.update(userDto, role);
    }

    private void setFile(UserCreateUpdateDto userDto, User user) throws IOException {
        Long fileId = fileService.saveFile(userDto.getProfilePicture());
        File file = fileService.getFileByFileId(fileId);

        user.setFile(file);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        // 비밀번호 암호화
        String hashedPassword = passwordEncoder.encode(newPassword);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        user.updatePassword(hashedPassword);
    }

    @Override
    public void updateLastLoginDateTime(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        user.updateLastLoginDateTime();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        user.delete(); // 회원 탈퇴 분기가 체크될 뿐 데이터가 디비에서 삭제되지는 않는다.
    }

    @Override
    public void deleteUsers(List<Long> idList) {
        for (Long id : idList) {
            deleteUser(id);
        }
    }
}
