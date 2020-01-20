package com.backend.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);

        // Project Banner 생성
        app.setBanner((environment, sourceClass, out) -> {
            out.println("  __      ____.    ____.                                  __");
            out.println(" / /     |    |   |    |__ __  ____ _____________  ____   \\ \\  ");
            out.println("/ /      |    |   |    |  |  \\/    \\\\____ \\_  __ \\/  _ \\   \\ \\ ");
            out.println("\\ \\  /\\__|    /\\__|    |  |  /   |  \\  |_> >  | \\(  <_> )  / / ");
            out.println(" \\_\\ \\________\\________|____/|___|  /   __/|__|   \\____/  /_/  ");
            out.println("                                  \\/|__|");
            out.println(":: Project :: " + environment.getProperty("project.name"));
            out.println(":: Version :: " + environment.getProperty("project.version"));
            out.println(" ");
        });

        // Project Runner 실행
        app.run(args);
    }
}
