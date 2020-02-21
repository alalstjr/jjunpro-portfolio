package com.jjunpro.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.project.security.filter.FormLoginFilter;
import com.jjunpro.project.security.handler.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.project.security.handler.FormLoginAuthenticationSuccessHandler;
import com.jjunpro.project.security.provider.FormLoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* FormLoginFilter 검증에 필요한 객체 */
    private final ObjectMapper                          objectMapper;
    private final FormLoginAuthenticationProvider       formLoginProvider;
    private final FormLoginAuthenticationSuccessHandler formLoginSuccessHandler;
    private final FormLoginAuthenticationFailuerHandler formLoginFailuerHandler;

    /*
     * AuthenticationManager 주입받아서 사용하려면
     * authenticationManagerBean() Override 해서 Bean 으로 직접 주입해줘야 합니다.
     *  */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
     * 인증이 이루어지는 Provider 설정
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.formLoginProvider);
    }

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

        /* UsernamePasswordAuthenticationFilter 이전에 FormLoginFilter 를 등록합니다. */
        http.addFilterBefore(
                formLoginFilter(),
                UsernamePasswordAuthenticationFilter.class
        );
    }

    private FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter(
                "/signin",
                objectMapper,
                formLoginSuccessHandler,
                formLoginFailuerHandler
        );

        /* FormLoginFilter 를 Filter 에 등록하여 인증을 하도록 설정합니다. */
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }
}
