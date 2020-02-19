package com.jjunpro.project.common;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class CustomBanner implements Banner {

    @Override
    public void printBanner(
            Environment environment,
            Class<?> sourceClass,
            PrintStream out
    ) {
        out.println("  __      ____.    ____.                                  __");
        out.println(" / /     |    |   |    |__ __  ____ _____________  ____   \\ \\  ");
        out.println("/ /      |    |   |    |  |  \\/    \\\\____ \\_  __ \\/  _ \\   \\ \\ ");
        out.println("\\ \\  /\\__|    /\\__|    |  |  /   |  \\  |_> >  | \\(  <_> )  / / ");
        out.println(" \\_\\ \\________\\________|____/|___|  /   __/|__|   \\____/  /_/  ");
        out.println("                                  \\/|__|");
        out.println(":: Project :: " + environment.getProperty("project.name"));
        out.println(":: Version :: " + environment.getProperty("project.version"));
        out.println(":: State   :: " + environment.getProperty("project.state"));
        out.println(" ");
    }
}
