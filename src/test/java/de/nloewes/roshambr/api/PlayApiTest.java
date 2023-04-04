package de.nloewes.roshambr.api;

import de.nloewes.roshambr.model.dto.GameChoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayApiTest {

    private static final String PLAY_CPU_PATH = "/play/cpu";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayApiDelegate playApi;

    @Test
    public void testGet_isNotAllowed() throws Exception {
        mockMvc.perform(get(PLAY_CPU_PATH)).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testPost_isSuccessful() throws Exception {
        GameChoice choice = new GameChoice();
        choice.setPlayerChoice("ROCK");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(choice);
        mockMvc.perform(post(PLAY_CPU_PATH).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPost_Invalid() throws Exception {
        GameChoice choice = new GameChoice();
        choice.setPlayerChoice("INVALID");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(choice);
        mockMvc.perform(post(PLAY_CPU_PATH).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.httpStatus").value("400"))
                .andExpect(jsonPath("$.code").value("1"))
                .andExpect(jsonPath("$.message").value("Invalid GameChoice"))
                .andExpect(jsonPath("$.value").value("INVALID"));
    }

    @Test
    public void testPost_delegate_isSuccessful() {
        GameChoice choice = new GameChoice();
        choice.setPlayerChoice("ROCK");
        playApi.postMatch(choice);
    }


}
