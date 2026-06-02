package com.leandroftm.game_library_api.integration.igdb;

import com.leandroftm.game_library_api.domain.dto.GameSearchResponse;
import com.leandroftm.game_library_api.integration.igdb.client.IgdbClient;
import com.leandroftm.game_library_api.integration.igdb.dto.IgdbGameResponse;
import com.leandroftm.game_library_api.mapper.IgdbMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestIGDB {


//    @Bean
//    CommandLineRunner test(IgdbClient igdbClient) {
//        return args -> {
//            List<IgdbGameResponse> response = igdbClient.searchGames("fallout");
//            //String responseString = igdbClient.searchGame("fallout");
//            //System.out.println(responseString);
//
//            if (response != null) {
//                response.forEach(games -> {
//                    System.out.println("Game: " + games.name());
//
//                    if (games.firstReleaseDate() != null) {
//                        Instant instant = Instant.ofEpochSecond(games.firstReleaseDate());
//                        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//                        System.out.println("    First release date: " + date.toString());
//                    }
//                    if (games.platforms() != null) {
//                        List<String> platforms = new ArrayList<>();
//                        games.platforms().forEach(platform -> {
//                            platforms.add(platform.name());
//                        });
//                        System.out.println("    Platforms: " + String.join(", ", platforms));
//                    }
//                    if (games.genres() != null) {
//                        List<String> genres = new ArrayList<>();
//                        games.genres().forEach(genre -> {
//                           genres.add(genre.name());
//                        });
//                        System.out.println("    Genres: " + String.join(", ", genres));
//                    }
//                    System.out.println();
//                });
//            }
//        };
//    }

    @Bean
    CommandLineRunner command(IgdbClient client, IgdbMapper mapper) {
       return args -> {
           List<IgdbGameResponse> igdbResponse = client.searchGames("fallout");
           if (igdbResponse != null) {
               igdbResponse.forEach(response -> {
                   GameSearchResponse game = mapper.toResponse(response);
                   System.out.println(game);
               });
           }
       } ;
    }
}
