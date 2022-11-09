package cms.sogi_cms.cms.user.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;

    private String username; // 아이디
    private String password;
    private String passwordCheck;

    private String lastname; // 성
    private String firstname; // 이름

    private String email;
    private Boolean isMailing;

    private String phoneNumber;

    private String gender;
    private String birthday;

    private Boolean isBirthdaySolar;

    private String roadNameAddress; // 도로명 주소
    private String lotNumberAddress; // 지번 주소
    private String detailAddress; // 상세 주소
    private String zipCode; // 우편번호
    private String extraAddress; // 참고사항

    // TODO 프로필 사진

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ROLE_NAME")
//    private Role role;

    private String captcha; // 캡차
}
