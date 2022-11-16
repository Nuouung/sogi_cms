package cms.sogi_cms.cms.user.entity;

import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_USER")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // 아이디
    private String password;

    private LocalDateTime registeredDateTime;
    private LocalDateTime passwordLastUpdatedDateTime;
    private LocalDateTime lastLoginDateTime;

    private String name; // 전체 이름
    private String lastname; // 이름
    private String firstname; // 성

    private String email;
    private Boolean isMailing;

    private String phoneNumber;

    private String gender;
    private String birthday;
    private Boolean isBirthdaySolar;

    @Embedded
    private Address address;

    // 읽기 전용. 데이터베이스에서 조회해 가져오기 때문에 setter, builder, constructor는 없다.
    @OneToOne(mappedBy = "user")
    private File file;

    // TODO 프로필 사진

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ROLE_NAME")
//    private Role role;

    private boolean isActive;
    private boolean isDeleted;

    private String captcha; // 캡차

    public static User create(UserCreateUpdateDto dto, String hashedPassword, Role role) {
        Address address = getAddress(dto);

        String birthdayMonth = dto.getBirthdayMonth().length() == 1 ? "0" + dto.getBirthdayMonth() : dto.getBirthdayMonth();
        String birthdayDay = dto.getBirthdayDay().length() == 1 ? "0" + dto.getBirthdayDay() : dto.getBirthdayDay();

        return User.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .registeredDateTime(LocalDateTime.now())
                .passwordLastUpdatedDateTime(LocalDateTime.now())
                .lastLoginDateTime(LocalDateTime.now())
                .name(dto.getLastname() + dto.getFirstname())
                .lastname(dto.getLastname())
                .firstname(dto.getFirstname())
                .email(dto.getEmail())
                .isMailing(dto.getIsMailing())
                .phoneNumber(dto.getPhoneNumberFront() + "-" + dto.getPhoneNumberMiddle() + "-" + dto.getPhoneNumberLast())
                .gender(dto.getGender())
                .birthday(dto.getBirthdayYear() + "-" + birthdayMonth + "-" + birthdayDay)
                .isBirthdaySolar(dto.getIsBirthdaySolar())
                .address(address)
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public void update(UserCreateUpdateDto dto) {
        Address address = getAddress();

        String birthdayMonth = dto.getBirthdayMonth().length() == 1 ? "0" + dto.getBirthdayMonth() : dto.getBirthdayMonth();
        String birthdayDay = dto.getBirthdayDay().length() == 1 ? "0" + dto.getBirthdayDay() : dto.getBirthdayDay();


        this.username = dto.getUsername();
        this.registeredDateTime = LocalDateTime.now();
        this.passwordLastUpdatedDateTime = LocalDateTime.now();
        this.lastLoginDateTime = LocalDateTime.now();
        this.name = dto.getLastname() + dto.getFirstname();
        this.lastname = dto.getLastname();
        this.firstname = dto.getFirstname();
        this.email = dto.getEmail();
        this.isMailing = dto.getIsMailing();
        this.phoneNumber = dto.getPhoneNumberFront() + "-" + dto.getPhoneNumberMiddle() + "-" + dto.getPhoneNumberLast();
        this.gender = dto.getGender();
        this.birthday = dto.getBirthdayYear() + "-" + birthdayMonth + "-" + birthdayDay;
        this.isBirthdaySolar = dto.getIsBirthdaySolar();
        this.address = address;
        this.isActive = true;
        this.isDeleted = false;
    }

    public void updatePassword(String hashedPassword) {
        this.password = hashedPassword;
        this.passwordLastUpdatedDateTime = LocalDateTime.now();
    }

    public void updateLastLoginDateTime() {
        this.lastLoginDateTime = LocalDateTime.now();
    }

    public void removePassword() {
        this.password = "";
    }

    public void delete() {
        this.isDeleted = true;
        this.isActive = false;
    }

    private static Address getAddress(UserCreateUpdateDto dto) {
        return Address.builder()
                .roadNameAddress(dto.getRoadNameAddress())
                .lotNumberAddress(dto.getLotNumberAddress())
                .detailAddress(dto.getDetailAddress())
                .zipCode(dto.getZipCode())
                .extraAddress(dto.getExtraAddress())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return false;
    }
}
