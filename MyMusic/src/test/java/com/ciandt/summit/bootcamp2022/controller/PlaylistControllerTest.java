package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.service.impl.PlaylistServiceImpl;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import static com.ciandt.summit.bootcamp2022.config.Factory.AUTHORIZATION_BAERER;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PlaylistControllerTest {
    public static final String ID_PLAYLIST = "a39926f4-6acb-4497-884f-d4e5296ef652";
    @InjectMocks
    private PlaylistController playlistController;
    @Mock
    private PlaylistServiceImpl playlistService;
    @Mock
    private TokenAuthorizerService tokenAuthorizerService;
    @Mock
    private HttpServletRequest request;
    private MusicDto musicDto;
    private PlaylistDto playlistDto;

    @BeforeEach
    void setup() {
        musicDto = Factory.createMusicDto();
        playlistDto = Factory.createPlaylistDto();
    }

    @Test
    @DisplayName("When save music in a playlist, then return Response Entity")
    void whenSaveMusicInPlaylistThenReturnResponseEntity(){
        ResponseEntity<String> responseEntity= new ResponseEntity<>(Factory.MSG_200_OK, HttpStatus.CREATED);

        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenReturn(responseEntity);

        when(playlistService.saveMusicInPlaylist(musicDto ,ID_PLAYLIST, USER_NICKNAME)).thenReturn(playlistDto);

        when(request.getHeader("Authorization")).thenReturn(AUTHORIZATION_BAERER);

        var response = playlistController.saveMusicInPlaylist(musicDto, ID_PLAYLIST,USER_NICKNAME);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    @DisplayName("When delete music from playlist, then return Response Entity")
    void whenDeleteMusicFromPlaylistThenReturnResponseEntity(){
        ResponseEntity<String> responseEntity= new ResponseEntity<>(Factory.MSG_200_OK, HttpStatus.CREATED);

        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenReturn(responseEntity);

        doNothing().when(playlistService).deleteMusicFromPlaylist(anyString(), anyString());

        when(request.getHeader("Authorization")).thenReturn(AUTHORIZATION_BAERER);

        var response = playlistController.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Factory.MSG_200_MUSIC_DELETE_SUCCESSFULLY, response.getBody());
    }
}