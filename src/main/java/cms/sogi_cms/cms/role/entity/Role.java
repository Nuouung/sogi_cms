package cms.sogi_cms.cms.role.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    private String koreanName;
    private String description;

    private LocalDate registerDate;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<RoleAuthority> roleAuthorityList = new ArrayList<>();

    private boolean isAdmin; // 최고 관리자 역할인지
    private boolean isDefaultUser; // 기본회원
}
