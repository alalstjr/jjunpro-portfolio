package com.jjunpro.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebIocConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Spring Security 5 권장하는 인코더
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
