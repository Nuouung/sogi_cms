package cms.sogi_cms.configuration.security.filterChain;

import cms.sogi_cms.cms.support.SogiConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

@Configuration
@EnableWebSecurity
@Order(1)
@RequiredArgsConstructor
public class SecurityAdminConfiguration {

    private final FilterSecurityInterceptor filterSecurityInterceptor;

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {

        // 인가 처리는 추후 진행 예정
        http
                .antMatcher(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/**") // 필터 적용 범위. 어드민 보안 설정이므로 어드민 경로 이하를 잡는다.

                .authorizeRequests()
                .antMatchers(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/login*").permitAll()
                .anyRequest().authenticated()

                .and()
//                .addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class)

                .formLogin()
                .loginPage(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/login")
                .loginProcessingUrl(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/login")
                .defaultSuccessUrl(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/main")

                .and()
                .csrf().disable();

        return http.build();
    }
}
