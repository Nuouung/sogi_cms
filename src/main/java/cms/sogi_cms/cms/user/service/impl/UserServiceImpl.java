package cms.sogi_cms.cms.user.service.impl;

import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.repository.UserRepository;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Long saveUser(UserCreateUpdateDto userDto) {
        // 비밀번호 암호화
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        // 회원가입 기본 역할 조회 및 할당 TODO

        // 객체 생성 및 저장
        User user = User.create(userDto, hashedPassword, null);
        userRepository.save(user);

        return user.getId();
    }

    @Override
    public User getUserById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        foundUser.removePassword();

        return foundUser;
    }

    @Override
    public User getUserByUsername(String username) {
        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        foundUser.removePassword();

        return foundUser;
    }

    @Override
    public Paging<User> getUserList(UserSearch userSearch) {
        Paging<User> paging = userRepository.findPage(userSearch);

        List<User> contents = paging.getContents();
        for (User foundUser : contents) {
            foundUser.removePassword();
        }

        paging.setContents(contents);

        return paging;
    }

    @Override
    public Long getTotalNumber(UserSearch userSearch) {
        return userRepository.count(userSearch);
    }

    @Override
    public void updateUser(Long id, UserCreateUpdateDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        user.update(userDto);
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
