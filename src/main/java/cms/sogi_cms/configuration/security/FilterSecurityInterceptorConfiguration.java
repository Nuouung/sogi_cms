package cms.sogi_cms.configuration.security;

import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.service.RoleService;
import cms.sogi_cms.cms.security.authorization.metasource.UrlFilterInvocationSecurityMetadataSource;
import cms.sogi_cms.cms.security.authorization.metasource.UrlResourceMapFactoryBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FilterSecurityInterceptorConfiguration {

    private final AuthorityService authorityService;

    // 인가 처리를 담당하는 인터셉터 (필터다 근데?)
    // 데이터베이스가 비워져 있는 상태에서 프로젝트를 돌릴 경우 (ddl-auto를 create로 했을 경우)
    // 디비에서 인가 설정을 가져오는 빈의 설정보다 데이터베이스에 인가 설정이 초기화되는 시점이 느려 제대로된 설정이 되지 않을 수 있다.
    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();

        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(
                new AffirmativeBased(
                        List.of(new RoleVoter())));

        return filterSecurityInterceptor;
    }

    @Bean
    public UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        return new UrlFilterInvocationSecurityMetadataSource(urlResourceMapFactoryBean().getObject());
    }

    @Bean
    public UrlResourceMapFactoryBean urlResourceMapFactoryBean() {
        return new UrlResourceMapFactoryBean(authorityService);
    }
}
