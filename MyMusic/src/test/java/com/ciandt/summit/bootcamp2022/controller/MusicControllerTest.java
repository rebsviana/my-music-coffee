package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.MusicServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MusicControllerTest {

    public static final String ID_MUSIC = "123456";
    public static final String NAME_MUSIC = "Can't Come Back";
    public static final String ID_ARTIST = "123465";
    public static final String NAME_ARTIST = "Bruno";
    @InjectMocks
    private MusicController controller;

    @Mock
    private MusicServiceImpl musicService;

    private MusicDto musicDto;

    @BeforeEach
    void setup() {
        musicDto = new MusicDto(ID_MUSIC, NAME_MUSIC, new Artist(ID_ARTIST, NAME_ARTIST));
    }

    @Test
    @DisplayName("when search some music with tilter then return response entity with list of MusicDto")
    void whenGetMusicByNameOrArtistWithFilterThenReturnReponseEntityWithListMusicDto(){
        when(musicService.getMusicByNameOrArtist(anyString())).thenReturn(List.of(musicDto));

        var response = controller.getMusicByNameOrArtistWithFilter("Bruno");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(List.of(musicDto), response.getBody());
    }

}