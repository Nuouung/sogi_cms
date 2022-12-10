package cms.sogi_cms.cms.security.authentication.model;

import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
public class FormUserDetails implements UserDetails {

    private final User user;
    private final String roleName;

    public FormUserDetails(User user, String roleName) {
        this.user = user;
        this.roleName = roleName;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        // 활성화되었고 탈퇴하지 않음 (is active and is not deleted) (activated but also not deleted)
        return user.isActive() && !user.isDeleted();
    }
}
