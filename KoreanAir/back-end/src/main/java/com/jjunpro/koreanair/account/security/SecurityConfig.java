package com.jjunpro.koreanair.account.security;

import java.util.Arrays;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.koreanair.account.security.filters.FilterSkipMatcher;
import com.jjunpro.koreanair.account.security.filters.FormLoginFilter;
import com.jjunpro.koreanair.account.security.filters.JwtAuthenticationFilter;
import com.jjunpro.koreanair.account.security.hendlers.FormLoginAuthenticationSuccessHandler;
import com.jjunpro.koreanair.account.security.hendlers.FormLoginFailuerHandler;
import com.jjunpro.koreanair.account.security.hendlers.JwtAuthenticationFailureHandler;
import com.jjunpro.koreanair.account.security.jwt.HeaderTokenExtractor;
import com.jjunpro.koreanair.account.security.providers.FormLoginAuthenticationProvider;
import com.jjunpro.koreanair.account.security.providers.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Autowired
	private FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
	
	@Autowired
	private FormLoginFailuerHandler fromLoginFailuerHandler;
	
	@Autowired
	private FormLoginAuthenticationProvider provider;
	
    @Autowired
    private JwtAuthenticationProvider jwtProvider;
    
    @Autowired
    private JwtAuthenticationFailureHandler jwtFailureHandler;

    @Autowired
    private HeaderTokenExtractor headerTokenExtractor;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	protected FormLoginFilter formLoginFilter() throws Exception 
	{
		FormLoginFilter filter = new FormLoginFilter("/formlogin", formLoginAuthenticationSuccessHandler, fromLoginFailuerHandler);
		filter.setAuthenticationManager(super.authenticationManagerBean());
		
		return filter;
	}
	

    protected JwtAuthenticationFilter jwtFilter() throws Exception 
    {
        FilterSkipMatcher matcher = new FilterSkipMatcher(Arrays.asList("/formlogin", "/social"), "/api/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtFailureHandler, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth
		.authenticationProvider(this.provider)
		.authenticationProvider(this.jwtProvider);
	}

	// 생성한 filter 등록은 configure 메서드로  
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		// 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
		http
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
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
}
