package com.jjunpro.project;

import com.jjunpro.project.common.CustomBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setBanner(new CustomBanner());
		app.run(args);
	}
}
