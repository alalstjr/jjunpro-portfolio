package com.backend.koreanair.config;

import com.backend.koreanair.enums.EnumMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Order(1)
@Configuration
public class WebMvcConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public EnumMapper enumMapper() {
        return new EnumMapper();
    }
}
