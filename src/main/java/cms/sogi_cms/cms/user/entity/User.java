package cms.sogi_cms.cms.user.entity;

import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import lombok.*;

import javax.management.relation.Role;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // 아이디
    private String password;

    private LocalDateTime registeredDateTime;
    private LocalDateTime passwordLastUpdatedDateTime;
    private LocalDateTime lastLoginDateTime;

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

    // TODO 프로필 사진

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ROLE_NAME")
//    private Role role;

    private boolean isActive;
    private boolean isDeleted;

    private String captcha; // 캡차

    public static User create(UserCreateUpdateDto dto, String hashedPassword, Role role) {
        Address address = Address.builder()
                .roadNameAddress(dto.getRoadNameAddress())
                .lotNumberAddress(dto.getLotNumberAddress())
                .detailAddress(dto.getDetailAddress())
                .zipCode(dto.getZipCode())
                .extraAddress(dto.getExtraAddress())
                .build();

        return User.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .registeredDateTime(LocalDateTime.now())
                .passwordLastUpdatedDateTime(LocalDateTime.now())
                .lastLoginDateTime(LocalDateTime.now())
                .lastname(dto.getLastname())
                .firstname(dto.getFirstname())
                .email(dto.getEmail())
                .isMailing(dto.getIsMailing())
                .phoneNumber(dto.getPhoneNumberFront() + "-" + dto.getPhoneNumberMiddle() + "-" + dto.getPhoneNumberLast())
                .gender(dto.getGender())
                .birthday(dto.getBirthdayYear() + "-" + dto.getBirthdayMonth() + "-" + dto.getBirthdayDay())
                .isBirthdaySolar(dto.getIsBirthdaySolar())
                .address(address)
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public void update(UserCreateUpdateDto dto) {
        Address address = Address.builder()
                .roadNameAddress(dto.getRoadNameAddress())
                .lotNumberAddress(dto.getLotNumberAddress())
                .detailAddress(dto.getDetailAddress())
                .zipCode(dto.getZipCode())
                .extraAddress(dto.getExtraAddress())
                .build();

        this.username = dto.getUsername();
        this.registeredDateTime = LocalDateTime.now();
        this.passwordLastUpdatedDateTime = LocalDateTime.now();
        this.lastLoginDateTime = LocalDateTime.now();
        this.lastname = dto.getLastname();
        this.firstname = dto.getFirstname();
        this.email = dto.getEmail();
        this.isMailing = dto.getIsMailing();
        this.phoneNumber = dto.getPhoneNumberFront() + "-" + dto.getPhoneNumberMiddle() + "-" + dto.getPhoneNumberLast();
        this.gender = dto.getGender();
        this.birthday = dto.getBirthdayYear() + "-" + dto.getBirthdayMonth() + "-" + dto.getBirthdayDay();
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
}
