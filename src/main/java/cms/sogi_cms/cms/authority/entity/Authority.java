package cms.sogi_cms.cms.authority.entity;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorityName;
    private String description;

    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;
    private String urlPath;

    private int authorityPriority;

    private boolean isDefault;

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