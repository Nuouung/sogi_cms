package cms.sogi_cms.cms.authority.dto;

import cms.sogi_cms.cms.authority.entity.HttpMethod;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class AuthorityCreateUpdateDto {

    private Long id;

    private String authorityName;
    private String authorityKoreanName;

    private String description;

    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;
    private String urlPath;

    private int authorityPriority;

    private boolean isDefault;
}
