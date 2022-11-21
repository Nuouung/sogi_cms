package cms.sogi_cms.cms.configuration.service;

import cms.sogi_cms.cms.configuration.entity.Configuration;
import cms.sogi_cms.cms.configuration.entity.OptionType;

/**
 * 설정 클래스는 애플리케이션 전반에 걸친 전역 설정을 부여하는 클래스이기 때문에 추가나 삭제가 매우 드물게 일어난다.
 * 사실상의 핵심은 이미 설정된 설정의 값을 변경하는 것이 중요하기 때문에 추가는 간단하게 구현하고 삭제는 개발자가 데이터베이스에서 직접 삭제하는 것으로 작업한다.
 * 즉, delete는 구현되지 않는다.
 */
public interface ConfigurationService {

    // c
    void createConfiguration(String configurationId,
                             String optionId,
                             String optionValue,
                             String optionName,
                             OptionType optionType,
                             String optionDescription);

    // r
    Configuration getConfigurationById(String configurationId, String optionId);

    // u
    void updateConfiguration(String configurationId, String optionId, String optionValue); // 값만 바꿀 수 있음
}
