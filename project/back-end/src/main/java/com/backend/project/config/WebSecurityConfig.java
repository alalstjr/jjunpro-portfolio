package com.backend.project.config;

import com.backend.project.security.filter.FilterSkipMatcher;
import com.backend.project.security.filter.FormLoginFilter;
import com.backend.project.security.filter.JwtAuthenticationFilter;
import com.backend.project.security.hendler.FormLoginAuthenticationFailuerHandler;
import com.backend.project.security.hendler.FormLoginAuthenticationSuccessHandler;
import com.backend.project.security.hendler.JwtAuthenticationFailureHandler;
import com.backend.project.security.jwt.HeaderTokenExtractor;
import com.backend.project.security.provider.FormLoginAuthenticationProvider;
import com.backend.project.security.provider.JwtAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FormLoginAuthenticationProvider provider;
    @Autowired
    private FormLoginAuthenticationSuccessHandler successHandler;
    @Autowired
    private FormLoginAuthenticationFailuerHandler failureHandler;

    @Autowired
    private JwtAuthenticationProvider jwtProvider;
    @Autowired
    private JwtAuthenticationFailureHandler jwtFailureHandler;
    @Autowired
    private HeaderTokenExtractor headerTokenExtractor;

    @Autowired
    private ObjectMapper objectMapper;

    // Bean 관리
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        configuration.addExposedHeader("WWW-Authenticate");
        configuration.addExposedHeader("Access-Control-Allow-Origin");
        configuration.addExposedHeader("Access-Control-Allow-Headers");

        configuration.addExposedHeader("X-Total-Count");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Filter 관리
    private FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter =
                new FormLoginFilter("/api/account/login", objectMapper, successHandler, failureHandler);

        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    private JwtAuthenticationFilter jwtFilter() throws Exception {
        List<String> skipPath = new ArrayList<String>();

        skipPath.add("POST,/api/account");
        skipPath.add("GET,/api/account");
        skipPath.add("GET,/api/account/*");

        skipPath.add("GET,/api/store/**");

        skipPath.add("GET,/api/university/**");

        FilterSkipMatcher matcher = new FilterSkipMatcher(skipPath, "/api/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtFailureHandler, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    // Provider 관리
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.provider)
                .authenticationProvider(this.jwtProvider);
    }

    // Config 관리
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .cors()
                .and();

        http
                .csrf()
                .disable();

        http
                .headers()
                .frameOptions()
                .disable();

        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Total-Count", "10"));
    }
}
