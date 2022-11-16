package cms.sogi_cms.cms.authority.entity;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_AUTHORITY")
public class Authority implements GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorityName;
    private String authorityKoreanName;

    private String description;

    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;
    private String urlPath;

    private int authorityPriority; // 기본값 100. 값이 커질수록 높은 권한

    private boolean isDefault; // 기본 권한인지

    @OneToMany(mappedBy = "authority")
    private List<RoleAuthority> roleAuthorityList = new ArrayList<>();

    private LocalDateTime registerDateTime;
    private LocalDateTime lastModifiedDateTime;

    @Override
    public String getAuthority() {
        return authorityName;
    }

    public static Authority create(AuthorityCreateUpdateDto dto) {
        return Authority.builder()
                .authorityName(dto.getAuthorityName())
                .authorityKoreanName(dto.getAuthorityKoreanName())
                .description(dto.getDescription())
                .httpMethod(dto.getHttpMethod())
                .urlPath(dto.getUrlPath())
                .authorityPriority(dto.getAuthorityPriority())
                .isDefault(dto.isDefault())
                .registerDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .build();
    }

    public void update(AuthorityCreateUpdateDto dto) {
        this.authorityName = dto.getAuthorityName();
        this.description = dto.getDescription();
        this.httpMethod = dto.getHttpMethod();
        this.urlPath = dto.getUrlPath();
        this.authorityPriority = dto.getAuthorityPriority();
        this.isDefault = dto.isDefault();
        this.registerDateTime = LocalDateTime.now();
        this.lastModifiedDateTime = LocalDateTime.now();
    }
}
