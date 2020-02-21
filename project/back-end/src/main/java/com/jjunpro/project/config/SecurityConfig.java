package com.jjunpro.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.project.security.filter.FormLoginFilter;
import com.jjunpro.project.security.handler.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.project.security.handler.FormLoginAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* FormLoginFilter 검증에 필요한 객체 */
    private final ObjectMapper                          objectMapper;
    private final FormLoginAuthenticationSuccessHandler successHandler;
    private final FormLoginAuthenticationFailuerHandler failureHandler;

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

        http.cors();


        /**
         * REST 서버설정 (불필요한 Filter 제거)
         *
         * csrf.disable      : 쿠키,세션 을 쓰지 않는 REST 서버의 경우에는 CSRF 공격에 대한 방지가 필요하지 않습니다.
         * formLogin.disable : REST 서버이므로 formLoginFilter 를 사용하지 않습니다.
         * logout.disable    : REST 서버이므로 logoutFilter 를 사용하지 않습니다.
         */
        http
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .logout()
                .disable();
    }

    private FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter(
                "/account/login",
                objectMapper,
                successHandler,
                failureHandler
        );

        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }
}
