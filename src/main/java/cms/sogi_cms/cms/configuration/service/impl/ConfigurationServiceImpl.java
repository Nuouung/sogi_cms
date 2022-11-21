package cms.sogi_cms.cms.configuration.service.impl;

import cms.sogi_cms.cms.configuration.entity.Configuration;
import cms.sogi_cms.cms.configuration.entity.ConfigurationId;
import cms.sogi_cms.cms.configuration.entity.OptionType;
import cms.sogi_cms.cms.configuration.repository.ConfigurationRepository;
import cms.sogi_cms.cms.configuration.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    @Override
    public void createConfiguration(String configurationId, String optionId, String optionValue, String optionName, OptionType optionType, String optionDescription) {
        Configuration configuration = Configuration.builder()
                .configurationId(new ConfigurationId(configurationId, optionId))
                .optionValue(optionValue)
                .optionName(optionName)
                .optionType(optionType)
                .optionDescription(optionDescription)
                .build();

        configurationRepository.save(configuration);
    }

    @Override
    public Configuration getConfigurationById(String configurationId, String optionId) {
        return configurationRepository.findById(new ConfigurationId(configurationId, optionId))
                .orElseThrow(() -> new EntityNotFoundException("설정 정보를 찾을 수 없습니다."));
    }

    @Override
    public void updateConfiguration(String configurationId, String optionId, String optionValue) {
        Configuration configuration = configurationRepository.findById(new ConfigurationId(configurationId, optionId))
                .orElseThrow(() -> new EntityNotFoundException("설정 정보를 찾을 수 없습니다."));

        configuration.update(optionValue);
    }
}
