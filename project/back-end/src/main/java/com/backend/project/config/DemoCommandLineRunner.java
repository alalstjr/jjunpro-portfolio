package com.backend.project.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DemoCommandLineRunner implements CommandLineRunner
{
    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("======v.0.0.1=======" + Arrays.toString(args));
    }
}