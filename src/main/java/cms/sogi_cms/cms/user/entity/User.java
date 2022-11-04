package cms.sogi_cms.cms.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username; // 아이디
    private String password;

    private LocalDateTime registeredDateTime;
    private LocalDateTime passwordLastUpdatedDateTime;
    private LocalDateTime lastLoginDateTime;

    private String lastname; // 이름
    private String firstname; // 성

    private String email;
    private boolean isMailing;

    private String phoneNumber;

    private String gender;
    private String birthday;
    private boolean isBirthdaySolar;

    @Embedded
    private Address address;

    // TODO 프로필 사진

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ROLE_NAME")
//    private Role role;

    private boolean isActive;
    private boolean isDeleted;

    private String captcha; // 캡차
}
