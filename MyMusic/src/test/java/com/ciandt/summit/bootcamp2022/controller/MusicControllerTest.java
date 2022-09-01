package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.MusicServiceImpl;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MusicControllerTest {

    public static final String ID_MUSIC = "123456";
    public static final String NAME_MUSIC = "Can't Come Back";
    public static final String ID_ARTIST = "123465";
    public static final String NAME_ARTIST = "Bruno";
    public static final String NAME_TOKEN = "Bruno";
    public static final String TOKEN = "123456789";
    @InjectMocks
    private MusicController controller;

    @Mock
    private MusicServiceImpl musicService;

    @Mock
    private TokenAuthorizerService tokenAuthorizerService;

    private MusicDto musicDto;

    @BeforeEach
    void setup() {
        musicDto = new MusicDto(ID_MUSIC, NAME_MUSIC, new ArtistDto(ID_ARTIST, NAME_ARTIST));
    }

    @Test
    @DisplayName("When search some music with filter then return response entity with list of MusicDto")
    void whenGetMusicByNameOrArtistWithFilterThenReturnReponseEntityWithListMusicDto(){
        ResponseEntity<String> responseEntity= new ResponseEntity<>("ok", HttpStatus.CREATED);

        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString(), anyString())).thenReturn(responseEntity);

        when(musicService.getMusicByNameOrArtist(anyString())).thenReturn(List.of(musicDto));

        var response = controller.getMusicByNameOrArtistWithFilter(NAME_MUSIC, NAME_TOKEN, TOKEN);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(List.of(musicDto), response.getBody());
    }

    @Test
    @DisplayName("When search some music with filter and token unauthorized then return UnauthorizedAccessException")
    void whenGetMusicByNameOrArtistWithFilterThenReturnUnauthorizedAccessException(){
        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString(), anyString())).thenThrow(new UnauthorizedAccessException());

        var exception = assertThrows(UnauthorizedAccessException.class,
                () -> controller.getMusicByNameOrArtistWithFilter(NAME_MUSIC, NAME_TOKEN, TOKEN));

        assertNotNull(exception);
        assertEquals(UnauthorizedAccessException.MESSAGE, exception.getMessage());
        assertEquals(UnauthorizedAccessException.class, exception.getClass());
    }
}