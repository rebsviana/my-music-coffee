package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.PlaylistServiceImpl;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.TokenAuthorizerService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PlaylistControllerTest {
    public static final String ID_MUSIC = "123456";
    public static final String NAME_MUSIC = "Can't Come Back";
    public static final String ID_ARTIST = "123465";
    public static final String NAME_ARTIST = "Bruno";
    public static final String ID_PLAYLIST = "a39926f4-6acb-4497-884f-d4e5296ef652";

    @InjectMocks
    private PlaylistController playlistController;
    @Mock
    private PlaylistServiceImpl playlistService;
    @Mock
    private TokenAuthorizerService tokenAuthorizerService;
    private MusicDto musicDto;
    private PlaylistDto playlistDto;

    @BeforeEach
    void setup() {
        musicDto = new MusicDto(ID_MUSIC, NAME_MUSIC, new ArtistDto(ID_ARTIST, NAME_ARTIST));
        playlistDto = new PlaylistDto(ID_PLAYLIST);
    }

    @Test
    @DisplayName("When save music in a playlist, then return Response Entity")
    void whenSaveMusicInPlaylistThenReturnResponseEntity(){
        ResponseEntity<String> responseEntity= new ResponseEntity<>("ok", HttpStatus.CREATED);

        when(tokenAuthorizerService.verifyTokenAuthorizer()).thenReturn(responseEntity);

        when(playlistService.saveMusicInPlaylist(musicDto ,ID_PLAYLIST)).thenReturn(playlistDto);

        var response = playlistController.saveMusicInPlaylist(musicDto, ID_PLAYLIST);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }
}