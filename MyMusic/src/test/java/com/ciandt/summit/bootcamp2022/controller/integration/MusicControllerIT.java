package com.ciandt.summit.bootcamp2022.controller.integration;

import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MusicControllerIT {
    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;
    @Autowired
    private HttpServletRequest request;

    @BeforeEach
    void setup() {
        //tokenAuthorizerService.verifyTokenAuthorizer(request.getHeader("Authorization"));
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When find music by name or artist then return list of MusicDto")
    void whenFindMusicByNameOrArtistThenReturnListOfMusicDto() throws Exception {

       ResultActions result =
               mockMvc.perform(get("/api/v1/music?filtro={MUSIC_NAME}", MUSIC_NAME)
               .accept(MediaType.APPLICATION_JSON));

       result.andExpect(status().isOk());
       result.andExpect(jsonPath("$.data").exists());
       result.andExpect(jsonPath("$.data[0].name").value("24K Magic"));
    }
}
