package cms.sogi_cms.cms.user.entity;

import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_USER")
public class User {

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

    @OneToOne
    @JoinColumn(name = "PROFILE_ID")
    private File file;

    @OneToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

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
                .role(role)
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public void update(UserCreateUpdateDto dto, Role role) {
        Address address = getAddress();

        String birthdayMonth = dto.getBirthdayMonth().length() == 1 ? "0" + dto.getBirthdayMonth() : dto.getBirthdayMonth();
        String birthdayDay = dto.getBirthdayDay().length() == 1 ? "0" + dto.getBirthdayDay() : dto.getBirthdayDay();


//        this.username = dto.getUsername(); 회원 아이디는 변경할 수 없다.
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
        this.role = role;
        this.isActive = dto.getIsActive();
        this.isDeleted = dto.getIsDeleted();

        // 탈퇴 회원은 휴면 처리하지 않는다.
        if (isDeleted) isActive = true;
    }

    public void updateRole(Role role) {
        this.role = role;
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

    public void setFile(File file) {
        this.file = file;
    }
}
