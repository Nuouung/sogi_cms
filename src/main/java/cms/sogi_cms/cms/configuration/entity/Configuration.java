package cms.sogi_cms.cms.configuration.entity;

import cms.sogi_cms.cms.support.SogiConstant;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_CONFIGURATION")
public class Configuration {

    @EmbeddedId
    private ConfigurationId configurationId; // configurationId와 optionId가 복합키로 되어 있다.

    private String optionValue;

    private String optionName;

    @Enumerated(EnumType.STRING)
    private OptionType optionType; // 옵션 유형. text, radio, checkbox, textarea

    private String optionDescription; // 뷰단의 사용자에게 보여줄 옵션에 대한 설명

    public void update(String optionValue) {
        this.optionValue = optionValue;
    }
}
