package com.leandroftm.game_library_api.integration.igdb;

import com.leandroftm.game_library_api.integration.igdb.client.IgdbClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {

    @Bean
    CommandLineRunner test(IgdbClient igdbClient) {
        return args -> {
            String response = igdbClient.searchGame("zelda");
            System.out.println("paçoca");
            System.out.println(response);
        };
    }
}
