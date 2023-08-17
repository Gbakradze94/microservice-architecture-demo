package com.microservice.controller;

import com.microservice.model.SongMetaData;
import com.microservice.util.PostgresExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(value = {PostgresExtension.class})
@TestPropertySource(locations = "classpath:application.properties")
public class SongControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldSaveSong() throws Exception {
        SongMetaData songMetaData = SongMetaData.builder()
                .songId(1)
                .name("Hey Jude")
                .length("7:18")
                .album("Tech")
                .artist("The Beatles Again")
                .year("1968")
                .build();

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(songMetaData)));

        perform.andExpect(status().isCreated());
        perform.andExpect(jsonPath("$.songId", notNullValue()));
    }
}
