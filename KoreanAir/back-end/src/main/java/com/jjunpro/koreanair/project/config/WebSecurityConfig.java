package com.jjunpro.koreanair.project.config;

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

import com.jjunpro.koreanair.project.security.filter.FormLoginFilter;
import com.jjunpro.koreanair.project.security.hendlers.FormLoginAuthenticationFailuerHandler;
import com.jjunpro.koreanair.project.security.hendlers.FormLoginAuthenticationSuccessHandler;
import com.jjunpro.koreanair.project.security.provider.FormLoginAuthenticationProvider;

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
    
    protected FormLoginFilter formLoginFilter() throws Exception {
    	FormLoginFilter filter = new FormLoginFilter("/api/user/login", formLoginAuthenticationSuccessHandler, formLoginAuthenticationFailuerHandler);
    	filter.setAuthenticationManager(super.authenticationManagerBean());
    	
    	return filter;
    }
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(this.provider);
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
        .authorizeRequests()
        .antMatchers("/h2-console**").permitAll();
		
		http
		.addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}