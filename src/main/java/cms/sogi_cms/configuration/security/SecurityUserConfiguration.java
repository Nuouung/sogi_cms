package cms.sogi_cms.configuration.security;

import cms.sogi_cms.cms.support.SogiConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
public class SecurityUserConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.mvcMatcher(SogiConstant.SITE_PATH + "/**")
                .authorizeRequests().anyRequest().denyAll();
    }
}
