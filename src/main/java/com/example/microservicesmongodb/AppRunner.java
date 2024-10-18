package com.example.microservicesmongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppRunner {

    @Bean
    CommandLineRunner run(ExampleService exampleService) {
        return args -> exampleService.start();
    }
}
