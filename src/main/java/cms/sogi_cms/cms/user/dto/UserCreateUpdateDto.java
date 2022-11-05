package cms.sogi_cms.cms.user.dto;

import cms.sogi_cms.cms.user.entity.Address;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class UserCreateUpdateDto {

    private Long id;

    private String username; // 아이디
    private String password;

    private String lastname; // 이름
    private String firstname; // 성

    private String email;
    private boolean isMailing;

    private String phoneNumber;

    private String gender;
    private String birthday;
    private boolean isBirthdaySolar;

    private String address; // 주소
    private String addressSub; // 상세주소
    private String zipCode; // 우편번호

    // TODO 프로필 사진

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ROLE_NAME")
//    private Role role;

    private String captcha; // 캡차
}
