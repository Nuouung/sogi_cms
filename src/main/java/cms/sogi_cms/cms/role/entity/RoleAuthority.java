package cms.sogi_cms.cms.role.entity;

import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_ROLE_AUTHORITY")
public class RoleAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_NAME")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_NAME")
    private Authority authority;
}
