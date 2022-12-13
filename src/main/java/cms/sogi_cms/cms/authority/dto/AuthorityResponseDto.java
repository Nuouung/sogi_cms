package cms.sogi_cms.cms.authority.dto;

import cms.sogi_cms.cms.authority.entity.HttpMethod;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorityResponseDto {

    private Long id;

    private String authorityName;
    private String authorityKoreanName;

    private String description;

    private String httpMethod;
    private String urlPath;

    private int priority; // 기본값 100. 값이 낮아질수록 높은 권한

    private boolean isDefault; // 사용자 지정 권한이 아닌 시스템 기본 권한인지

    private LocalDateTime registerDateTime;
    private LocalDateTime lastModifiedDateTime;
}
