package no.nnsn.quakemlwebservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/fdsnws/event/1/**")
                .antMatchers("/dataform")
                .antMatchers("/events-preview")
                .antMatchers("/error")
                .antMatchers("/");
    }
}
