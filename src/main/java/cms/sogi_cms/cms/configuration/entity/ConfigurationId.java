package cms.sogi_cms.cms.configuration.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ConfigurationId implements Serializable {

    @Serial
    private static final long serialVersionUID = 2875459864442894761L;

    private String configurationId;
    private String optionId;
}
