package com.example.microservicesmongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppRunner {

    @Bean
    CommandLineRunner run(
            MongoDB_CRUD_Operations crud,
            MongoDB_Transactions_Operations transactions,
            MongoDB_Aggregation_Operations aggregation,
            MongoDB_FIND_Operations find
    ) {
        return args -> {

            find.start();

            crud.start();

            transactions.start();

            aggregation.start();

        };
    }
}
