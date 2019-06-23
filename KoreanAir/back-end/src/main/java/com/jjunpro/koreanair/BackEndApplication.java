package com.jjunpro.koreanair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.jjunpro.koreanair.file.FileDemoApplication;
import com.jjunpro.koreanair.property.FileStorageProperties;


@SpringBootApplication
public class BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

}
