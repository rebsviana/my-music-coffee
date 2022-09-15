package com.ciandt.summit.bootcamp2022.controller.integration;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import javax.transaction.Transactional;

import static com.ciandt.summit.bootcamp2022.config.Factory.AUTHORIZATION_BAERER;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_NAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PlaylistControllerIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenAuthorizerService tokenAuthorizerService;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("When user common save music in playlist then add music to the playlist")
    void whenUserCommonSaveMusicInPlaylistThenAddMusicToThePlaylist() throws Exception {

        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenReturn(ResponseEntity.ok("Ok"));
        String jsonBody = objectMapper.writeValueAsString(Factory.createMusicDto());

        ResultActions result =
                mockMvc.perform(post("/api/playlists/{PLAYLIST_ID}/{USER_NICKNAME}/music", PLAYLIST_ID, USER_NICKNAME)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When delete music from playlist, then return Response Entity")
    void whenDeleteMusicFromPlaylistThenReturnResponseEntity() throws Exception {
        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenReturn(ResponseEntity.ok("Ok"));
        ResultActions result =
                mockMvc.perform(delete("/api/playlists/{playlistId}/musicas/{musicaId}", PLAYLIST_ID, MUSIC_ID));

        result.andExpect(status().isOk());
    }
}
