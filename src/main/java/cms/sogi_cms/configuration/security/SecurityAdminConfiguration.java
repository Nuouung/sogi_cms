package cms.sogi_cms.configuration.security;

import cms.sogi_cms.cms.support.SogiConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.FilterChain;

@Configuration
@Order(1)
public class SecurityAdminConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.mvcMatcher(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/**")
                .authorizeRequests().anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/sogi/admin/heheheheheh")
                .loginProcessingUrl("/hello/admin");
    }
}
