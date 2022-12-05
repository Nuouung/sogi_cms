package cms.sogi_cms.configuration.security;

import cms.sogi_cms.cms.security.authorization.metasource.UrlFilterInvocationSecurityMetadataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FilterSecurityInterceptorConfiguration {

    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    // 인가 처리를 담당하는 인터셉터 (필터다 근데?)
    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();

        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
        filterSecurityInterceptor.setAccessDecisionManager(
                new AffirmativeBased(
                        List.of(new RoleVoter())));

        return filterSecurityInterceptor;
    }
}
