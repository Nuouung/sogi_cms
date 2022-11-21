//package cms.sogi_cms.cms.configuration.service.impl;
//
//import cms.sogi_cms.cms.configuration.entity.Configuration;
//import cms.sogi_cms.cms.configuration.service.ConfigurationService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//@SpringBootTest
//class ConfigurationServiceImplTest {
//
//    @Autowired private ConfigurationService configurationService;
//
//    @Test
//    public void getConfigurationByIdTest() {
//        Configuration foundConfiguration = configurationService.getConfigurationById("file", "thumbnail_default_height");
//
//        Assertions.assertThat(foundConfiguration.getOptionValue()).isEqualTo("300");
//    }
//
//    @Test
//    public void updateConfigurationTest() {
//        configurationService.updateConfiguration("file", "thumbnail_default_height", "1000");
//        Configuration foundConfiguration = configurationService.getConfigurationById("file", "thumbnail_default_height");
//
//        Assertions.assertThat(foundConfiguration.getOptionValue()).isEqualTo("1000");
//    }
//}