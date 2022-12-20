package cms.sogi_cms.cms.support.init;

import cms.sogi_cms.cms.support.init.initializer.ArchiveInitializer;
import cms.sogi_cms.cms.support.init.initializer.ConfigurationInitializer;
import cms.sogi_cms.cms.support.init.initializer.RoleAuthorityInitializer;
import cms.sogi_cms.cms.support.init.initializer.UserInitializer;
import cms.sogi_cms.cms.support.utils.InitializeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class InitializeStarter {

    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlMode;

    private final ConfigurationInitializer configurationInitializer;
    private final UserInitializer userInitializer;
    private final RoleAuthorityInitializer roleAuthorityInitializer;
    private final ArchiveInitializer archiveInitializer;

    @PostConstruct
    public void dataInit() throws IOException {
        if (InitializeUtils.isDataInit(ddlMode)) {
            // 설정 데이터 삽입
            configurationInitializer.configurationDataInit();
            // 역할, 권한 데이터 삽입
            roleAuthorityInitializer.roleAuthorityDataInit();
            // 회원 데이터 삽입
            userInitializer.userDataInit();
            // 아카이브 데이터 삽입
            archiveInitializer.archiveDataInit();
        }
    }
}
