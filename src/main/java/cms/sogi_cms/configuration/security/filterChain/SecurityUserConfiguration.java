package cms.sogi_cms.configuration.security.filterChain;

import cms.sogi_cms.cms.support.SogiConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class SecurityUserConfiguration {

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        http
                .antMatcher(SogiConstant.SITE_PATH + "/**")

                .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
