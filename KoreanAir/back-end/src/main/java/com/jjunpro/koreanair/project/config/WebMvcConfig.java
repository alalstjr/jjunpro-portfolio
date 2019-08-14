package com.jjunpro.koreanair.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jjunpro.koreanair.project.enums.EnumMapper;
import com.jjunpro.koreanair.project.enums.UserRole;

@Configuration
public class WebMvcConfig {

	@Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("UserRole", UserRole.class);
        return enumMapper;
    }
}
