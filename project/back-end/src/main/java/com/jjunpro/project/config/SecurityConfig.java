package com.jjunpro.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.project.security.common.FilterSkipMatcher;
import com.jjunpro.project.security.filter.FormLoginAuthenticationFilter;
import com.jjunpro.project.security.filter.JwtAuthenticationFilter;
import com.jjunpro.project.security.handler.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.project.security.handler.FormLoginAuthenticationSuccessHandler;
import com.jjunpro.project.security.jwt.HeaderTokenExtractor;
import com.jjunpro.project.security.provider.FormLoginAuthenticationProvider;
import com.jjunpro.project.security.provider.JWTAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* FormLoginFilter 검증에 필요한 객체 */
    private final ObjectMapper objectMapper;

    private final FormLoginAuthenticationProvider       formLoginProvider;
    private final FormLoginAuthenticationSuccessHandler formLoginSuccessHandler;
    private final FormLoginAuthenticationFailuerHandler formLoginFailuerHandler;

    private final JWTAuthenticationProvider jwtProvider;
    private final HeaderTokenExtractor      headerTokenExtractor;

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
        auth
                .authenticationProvider(this.formLoginProvider)
                .authenticationProvider(this.jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

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
                        HttpMethod.GET,
                        "/account/check",
                        "/alarm"
                )
                .hasRole("USER");

        http
                .authorizeRequests()
                .mvcMatchers(
                        HttpMethod.POST,
                        "/university",
                        "/university/*",
                        "/university/like/*",
                        "/account/password/*",
                        "/comment"
                )
                .hasRole("USER");

        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.DELETE,
                        "/comment/*",
                        "/alarm/*")
                .hasRole("USER");

        /* 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다. */
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

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

        /**
         * UsernamePasswordAuthenticationFilter 이전에 FormLoginFilter, JwtFilter 를 등록합니다.
         * FormLoginFilter : 로그인 인증을 실시합니다.
         * JwtFilter       : 서버에 접근시 JWT 확인 후 인증을 실시합니다.
         * */
        http
                .addFilterBefore(
                        formLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    private FormLoginAuthenticationFilter formLoginFilter() throws Exception {
        FormLoginAuthenticationFilter filter = new FormLoginAuthenticationFilter(
                "/signin",
                objectMapper,
                formLoginSuccessHandler,
                formLoginFailuerHandler
        );

        /* FormLoginFilter 를 Filter 에 등록하여 인증을 하도록 설정합니다. */
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    private JwtAuthenticationFilter jwtFilter() throws Exception {
        List<String> skipPath = new ArrayList<>();

        skipPath.add("POST,/signin");

        skipPath.add("POST,/account");
        skipPath.add("GET,/account/*");

        skipPath.add("GET,/university/*");
        skipPath.add("GET,/university/search");
        skipPath.add("GET,/university/count/*");

        skipPath.add("GET,/comment/*");

        skipPath.add("GET,/store/**");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPath,
                "/**"
        );

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }
}
