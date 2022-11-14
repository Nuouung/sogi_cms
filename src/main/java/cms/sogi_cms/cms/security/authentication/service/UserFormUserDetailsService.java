package cms.sogi_cms.cms.security.authentication.service;

import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFormUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // User 객체가 UserDetails 구현 객체
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("조건에 맞는 회원을 찾을 수 없습니다."));
    }
}
