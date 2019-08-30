package com.jjunpro.koreanair.project.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jjunpro.koreanair.project.security.filter.FilterSkipMatcher;
import com.jjunpro.koreanair.project.security.filter.FormLoginFilter;
import com.jjunpro.koreanair.project.security.filter.JwtAuthenticationFilter;
import com.jjunpro.koreanair.project.security.hendlers.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.koreanair.project.security.hendlers.FormLoginAuthenticationSuccessHandler;
import com.jjunpro.koreanair.project.security.hendlers.JwtAuthenticationFailureHandler;
import com.jjunpro.koreanair.project.security.jwts.HeaderTokenExtractor;
import com.jjunpro.koreanair.project.security.provider.FormLoginAuthenticationProvider;
import com.jjunpro.koreanair.project.security.provider.JwtAuthenticationProvider;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	private FormLoginAuthenticationProvider provider;
	private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
	private FormLoginAuthenticationFailuerHandler formLoginAuthenticationFailuerHandler;

	private JwtAuthenticationProvider jwtProvider;
    private JwtAuthenticationFailureHandler jwtFailureHandler;
    private HeaderTokenExtractor headerTokenExtractor;
	
	/**
     * Bean 등록 관리 
     */
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
	       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	       source.registerCorsConfiguration("/**", configuration);
	       return source;
	}

    /**
     * Filter 관리 
     */
    protected FormLoginFilter formLoginFilter() throws Exception {
    	FormLoginFilter filter = new FormLoginFilter("/api/account/login", formLoginAuthenticationSuccessHandler, formLoginAuthenticationFailuerHandler);
    	filter.setAuthenticationManager(super.authenticationManagerBean());
    	
    	return filter;
    }
    protected JwtAuthenticationFilter jwtFilter() throws Exception {
    	List<String> skipPath = new ArrayList<String>();
    	skipPath.add("GET,board");
    	skipPath.add("GET,all/cate/");
    	skipPath.add("GET,board/page");
    	skipPath.add("POST,account");
    	skipPath.add("POST,account/login");
    	
        FilterSkipMatcher matcher = new FilterSkipMatcher(skipPath, "/api/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtFailureHandler, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }
    
    /**
     * Provider 관리 
     */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(this.provider)
		.authenticationProvider(this.jwtProvider);
	}
    
	/**
     * configure 관리 
     */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
		http
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// CORS 설정 
		http
		.cors().and();
		
		http
		.csrf()
		.disable();
		
		http
		.headers()
		.frameOptions()
		.disable();
		
		http
        .authorizeRequests()
        .antMatchers("/h2-console**").permitAll();
		
		http
		.addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}