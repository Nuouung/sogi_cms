package cms.sogi_cms.cms.role.entity;

import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_ROLE_AUTHORITY_RELATION")
public class RoleAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_ID")
    private Authority authority;

    public static RoleAuthority create(Role role, Authority authority) {
        return RoleAuthority.builder()
                .role(role)
                .authority(authority)
                .build();
    }
}
