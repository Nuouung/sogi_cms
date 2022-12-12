package cms.sogi_cms.cms.user.dto;

import cms.sogi_cms.cms.file.dto.FileResponseDto;
import lombok.Data;

import java.time.LocalDateTime;

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

    private boolean isActive; // 활성화여부
    private boolean isDeleted; // 삭제여부

    private LocalDateTime registeredDateTime;
    private LocalDateTime passwordLastUpdatedDateTime;
    private LocalDateTime lastLoginDateTime;

    private FileResponseDto file;

    private String roleName;
    private String roleKoreanName;

    private String captcha; // 캡차
}
