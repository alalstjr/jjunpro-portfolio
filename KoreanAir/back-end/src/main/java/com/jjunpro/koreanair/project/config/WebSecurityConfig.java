package com.jjunpro.koreanair.project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
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
	}
}