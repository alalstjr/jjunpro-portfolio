package com.backend.project.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class VersionLineRunner implements CommandLineRunner
{
    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("======v0.0.4=======" + Arrays.toString(args));
    }
}