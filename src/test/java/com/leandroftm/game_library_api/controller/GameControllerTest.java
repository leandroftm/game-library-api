package com.leandroftm.game_library_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandroftm.game_library_api.domain.dto.response.GameSearchResponse;
import com.leandroftm.game_library_api.service.IgdbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IgdbService igdbService;

    @Test
    @WithMockUser
    void shouldFindGameByIdSuccessfully() throws Exception {
        GameSearchResponse response = new GameSearchResponse(
                1L,
                "Fallout",
                LocalDate.now(),
                List.of("PC"),
                List.of("Role Playing Game")
        );
        when(igdbService.searchGameById(any(Long.class))).thenReturn(response);

        mockMvc.perform(get("/games/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Fallout"))
                .andExpect(jsonPath("$.platforms[*]").value("PC"))
                .andExpect(jsonPath("$.genres[*]").value("Role Playing Game"));
    }

    @Test
    @WithMockUser
    void shouldFindGamesByNameSuccessfully() throws Exception {
        Page<GameSearchResponse> page = new PageImpl<>(List.of(new GameSearchResponse(
                1L,
                "Fallout",
                LocalDate.now(),
                List.of("PC"),
                List.of("Role Playing Game")
        )));

        when(igdbService.searchGamesByName(any(String.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/games/search?name=${gameName}", "Fallout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(page)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[*].id").value(1))
                .andExpect(jsonPath("$.content[*].name").value("Fallout"))
                .andExpect(jsonPath("$.content[*].platforms[*]").value("PC"))
                .andExpect(jsonPath("$.content[*].genres[*]").value("Role Playing Game"));
    }
}
