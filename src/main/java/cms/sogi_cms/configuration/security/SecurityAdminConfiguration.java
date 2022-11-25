package cms.sogi_cms.configuration.security;

import cms.sogi_cms.cms.support.SogiConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class SecurityAdminConfiguration extends WebSecurityConfigurerAdapter {

    // FormLoginConfigurer에 대해 알아볼 것

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.mvcMatcher(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/**")
                .authorizeRequests().anyRequest().permitAll()
                .and().formLogin()
                .loginProcessingUrl("/sogi/admin/heheheheheh")
                .loginProcessingUrl("/hello/admin")
                .and()
                .csrf().disable();
    }
}
