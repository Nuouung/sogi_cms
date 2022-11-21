package cms.sogi_cms.cms.configuration.repository;

import cms.sogi_cms.cms.configuration.entity.Configuration;
import cms.sogi_cms.cms.configuration.entity.ConfigurationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, ConfigurationId> {
}
