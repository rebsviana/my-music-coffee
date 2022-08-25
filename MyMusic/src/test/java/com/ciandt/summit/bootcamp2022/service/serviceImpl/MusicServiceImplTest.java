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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

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
    @DisplayName("When find music by name or artist then return list of MusicDto")
    void whenFindMusicByNameOrArtistThenReturnListOfMusicDto() {
        when(musicRepository.findMusicByNameOrArtist(anyString()))
                .thenReturn(List.of(music));

        when(mapper.convertValue(music, MusicDto.class))
                .thenReturn(musicDto);

        var list = service.getMusicByNameOrArtist("Harley");

        assertThat(list.get(0).getName(), containsString("Harley"));
        assertNotEquals(list.get(0).getArtistId().getName(), containsString("Harley"));

        //TODO: Buscar metodo para comparar se há pelo menos um dos dois
    }

    @Test
    @DisplayName("When the name has less than three characters then return MinLengthRequiredException")
    void whenGetMusicByNameOrArtistThenReturnMinLengthRequiredException() {
        var exception = assertThrows(MinLengthRequiredException.class,
                () -> service.getMusicByNameOrArtist("aa"));

        assertNotNull(exception);
        assertEquals(MinLengthRequiredException.MESSAGE, exception.getMessage());
        assertEquals(MinLengthRequiredException.class, exception.getClass());
        verify(musicRepository, times(0)).findMusicByNameOrArtist(anyString());
    }

    @Test
    @DisplayName("When the list is empty then return NoContentException")
    void whenGetMusicByNameOrArtistThenReturnNoContentException() {
        when(musicRepository.findMusicByNameOrArtist(anyString()))
                .thenReturn(List.of());

        var exception = assertThrows(NoContentException.class,
                () -> service.getMusicByNameOrArtist("rebeca"));

        assertNotNull(exception);
        assertEquals(NoContentException.MESSAGE, exception.getMessage());
        assertEquals(NoContentException.class, exception.getClass());
    }
}