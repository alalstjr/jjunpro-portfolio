package com.jjunpro.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 권한 요청없이 접근 가능한 설정 */
        http
                .authorizeRequests()
                .mvcMatchers(
                        HttpMethod.POST,
                        "/account"
                )
                .permitAll();

        /* 권한(USER)이 필요한 접근 설정 */
        http
                .authorizeRequests()
                .mvcMatchers(
                        HttpMethod.POST,
                        "/account/**"
                )
                .hasRole("USER");
    }
}
