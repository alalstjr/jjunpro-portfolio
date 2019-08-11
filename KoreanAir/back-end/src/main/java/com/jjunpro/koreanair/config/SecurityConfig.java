package com.jjunpro.koreanair.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jjunpro.koreanair.security.filters.FilterSkipMatcher;
import com.jjunpro.koreanair.security.filters.FormLoginFilter;
import com.jjunpro.koreanair.security.filters.JwtAuthenticationFilter;
import com.jjunpro.koreanair.security.hendlers.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.koreanair.security.hendlers.FormLoginAuthenticationSuccessHandler;
import com.jjunpro.koreanair.security.hendlers.JwtAuthenticationFailureHandler;
import com.jjunpro.koreanair.security.jwt.HeaderTokenExtractor;
import com.jjunpro.koreanair.security.providers.FormLoginAuthenticationProvider;
import com.jjunpro.koreanair.security.providers.JwtAuthenticationProvider;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
	
	private FormLoginAuthenticationFailuerHandler formLoginAuthenticationFailuerHandler;
	
	private FormLoginAuthenticationProvider provider;
	
    private JwtAuthenticationProvider jwtProvider;
    
    private JwtAuthenticationFailureHandler jwtFailureHandler;

    private HeaderTokenExtractor headerTokenExtractor;
    
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
	
	protected FormLoginFilter formLoginFilter() throws Exception {
		FormLoginFilter filter = new FormLoginFilter("/api/user/login", formLoginAuthenticationSuccessHandler, formLoginAuthenticationFailuerHandler);
		filter.setAuthenticationManager(super.authenticationManagerBean());
		
		return filter;
	}
	
    protected JwtAuthenticationFilter jwtFilter() throws Exception {
    	log.info("도달"); 
    	List<String> skipPath = new ArrayList<String>();
    	skipPath.add("GET,board");
    	skipPath.add("GET,all/cate/");
    	skipPath.add("GET,board/page");
    	skipPath.add("POST,user");
    	skipPath.add("POST,user/login");
    	skipPath.add("POST,user/social");
    	
        FilterSkipMatcher matcher = new FilterSkipMatcher(skipPath, "/api/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtFailureHandler, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    } 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(this.provider)
		.authenticationProvider(this.jwtProvider);
	}
	
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
		.addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
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
}
