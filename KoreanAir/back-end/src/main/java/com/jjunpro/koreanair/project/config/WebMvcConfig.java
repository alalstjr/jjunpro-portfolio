package com.jjunpro.koreanair.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjunpro.koreanair.project.enums.EnumMapper;
import com.jjunpro.koreanair.project.enums.UserRole;

@Order(1)
@Configuration
public class WebMvcConfig {

	@Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("UserRole", UserRole.class);
        return enumMapper;
    }
	
	// BCryptPasswordEncoder Bean Up
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
    	return new ObjectMapper();
    }
}
