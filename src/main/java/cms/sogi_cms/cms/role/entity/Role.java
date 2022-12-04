package cms.sogi_cms.cms.role.entity;

import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_ROLE")
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
    private boolean isDefaultUser; // 기본 회원인지

    public static Role create(RoleCreateUpdateDto dto) {
        return Role.builder()
                .roleName(dto.getRoleName())
                .koreanName(dto.getKoreanName())
                .description(dto.getDescription())
                .registerDate(LocalDate.now())
                .isAdmin(dto.isAdmin())
                .isDefaultUser(dto.isDefaultUser())
                .build();
    }

    public void update(RoleCreateUpdateDto dto) {
        this.roleName = dto.getRoleName();
        this.koreanName = dto.getKoreanName();
        this.description = dto.getDescription();
        this.isDefaultUser = dto.isDefaultUser();
    }
}
