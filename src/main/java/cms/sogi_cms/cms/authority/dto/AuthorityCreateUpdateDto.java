package cms.sogi_cms.cms.authority.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class AuthorityCreateUpdateDto {

    private Long id;

    private String authorityName;
    private String description;

    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;
    private String urlPath;

    private int authorityPriority;

    private boolean isDefault;
}
