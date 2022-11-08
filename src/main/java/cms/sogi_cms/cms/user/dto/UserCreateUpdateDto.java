package cms.sogi_cms.cms.user.dto;

import cms.sogi_cms.cms.user.entity.Address;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class UserCreateUpdateDto {

    private Long id;

    @NotBlank
    private String username; // 아이디
    @NotBlank
    private String password;
    @NotBlank
    private String passwordCheck;

    @NotBlank
    private String lastname; // 성
    @NotBlank
    private String firstname; // 이름

    @NotBlank @Email
    private String email;
    private Boolean isMailing;

    @NotBlank @Digits(integer = 3, fraction = 0)
    private String phoneNumberFront;
    @NotBlank @Digits(integer = 4, fraction = 0)
    private String phoneNumberMiddle;
    @NotBlank @Digits(integer = 4, fraction = 0)
    private String phoneNumberLast;

    @NotBlank
    private String gender;
    @NotBlank
    private String birthdayYear;
    @NotBlank
    private String birthdayMonth;
    @NotBlank
    private String birthdayDay;

    private Boolean isBirthdaySolar;

    @NotBlank
    private String roadNameAddress; // 도로명 주소
    @NotBlank
    private String lotNumberAddress; // 지번 주소
    @NotBlank
    private String detailAddress; // 상세 주소
    @NotBlank
    private String zipCode; // 우편번호
    private String extraAddress; // 참고사항

    // TODO 프로필 사진

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ROLE_NAME")
//    private Role role;

    private String captcha; // 캡차
}
