package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MusicServiceImplTest {
    @Mock
    private MusicRepository musicRepository;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private MusicServiceImpl service;

    private Music music;
    private MusicDto musicDto;
    private Artist artist;

    @BeforeEach
    void setup() {
        artist = new Artist("1344", "David");
        music = new Music("12343", "Harley", artist);
        musicDto = new MusicDto("12343", "Harley", artist);
    }

    @Test
    void whenSearchMusicNameOrArtistNameThenReturnListOfMusicDto() {
        Mockito.when(musicRepository.searchMusicNameOrArtistName(anyString()))
                .thenReturn(List.of(music));

        Mockito.when(mapper.convertValue(music, MusicDto.class))
                .thenReturn(musicDto);

        var list = service.searchMusicNameOrArtistName("Harley");

        assertThat(list.get(0).getName(), containsString("Harley"));
        assertNotEquals(list.get(0).getArtistId().getName(), containsString("Harley"));

        //TODO: Buscar metodo para comparar se há pelo menos um dos dois
    }

    @Test
    @DisplayName("When the name has less than three characters then return MinLengthRequiredException")
    void whenSearchMusicNameOrArtistNameThenReturnMinLengthRequiredException() {
        var exception = assertThrows(MinLengthRequiredException.class,
                () -> service.searchMusicNameOrArtistName("aa"));

        assertNotNull(exception);
        assertEquals(MinLengthRequiredException.MESSAGE, exception.getMessage());
        assertEquals(MinLengthRequiredException.class, exception.getClass());
        verify(musicRepository, times(0)).searchMusicNameOrArtistName(anyString());
    }

    @Test
    @DisplayName("When the list is empty then return NoContentException")
    void whenSearchMusicNameOrArtistNameThenReturnNoContentException() {
        Mockito.when(musicRepository.searchMusicNameOrArtistName(anyString()))
                .thenReturn(List.of());

        var exception = assertThrows(NoContentException.class,
                () -> service.searchMusicNameOrArtistName("rebeca"));

        assertNotNull(exception);
        assertEquals(NoContentException.MESSAGE, exception.getMessage());
        assertEquals(NoContentException.class, exception.getClass());
    }
}