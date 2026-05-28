package com.leandroftm.game_library_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GameLibraryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameLibraryApiApplication.class, args);
	}

}
