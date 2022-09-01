package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.BadRequestPlaylistException;
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
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@SpringBootTest
class MusicServiceImplTest {
    public static final String MUSIC_ID = "12343";
    public static final String MUSIC_NAME = "Harley";
    public static final String ARTIST_ID = "1344";
    public static final String ARTIST_NAME = "David";
    @Mock
    private MusicRepository musicRepository;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private MusicServiceImpl service;
    private Music music;
    private MusicDto musicDto;
    private Artist artist;
    private ArtistDto artistDto;

    @BeforeEach
    void setup() {
        artist = new Artist(ARTIST_ID, ARTIST_NAME);
        music = new Music(MUSIC_ID, MUSIC_NAME, artist);
        artistDto = new ArtistDto(ARTIST_ID, ARTIST_NAME);
        musicDto = new MusicDto(MUSIC_ID, MUSIC_NAME, artistDto);
    }

    @Test
    @DisplayName("When find music by name or artist then return list of MusicDto")
    void whenFindMusicByNameOrArtistThenReturnListOfMusicDto() {
        when(musicRepository.findMusicByNameOrArtist(anyString()))
                .thenReturn(List.of(music));

        var list = service.getMusicByNameOrArtist("Harley");

        assertThat(list.get(0).getName(), containsString("Harley"));

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

    @Test
    @DisplayName("When find music by id then return a music")
    void whenGetMusicByIdThenReturnAMusic() {
        when(musicRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(music));

        var response = service.getMusicById(MUSIC_ID);

        assertNotNull(response);
        assertEquals(MusicDto.class, response.getClass());
        assertEquals(MUSIC_ID, response.getId());
        assertEquals(MUSIC_NAME, response.getName());
        assertEquals(ARTIST_ID, response.getArtistId().getId());
        assertEquals(ARTIST_NAME, response.getArtistId().getName());
    }

    @Test
    @DisplayName("When find music by id and music doesn't exist then return BadRequestPlaylistException")
    void whenGetMusicByIdAndMusicDoesntExistThenReturnBadRequestPlaylistException() {
        when(musicRepository.findById(anyString()))
                .thenThrow(new BadRequestPlaylistException("Music doesn't exist"));

        var exception = assertThrows(BadRequestPlaylistException.class,
                () -> service.getMusicById(MUSIC_ID));

        assertNotNull(exception);
        assertEquals("Music doesn't exist", exception.getMessage());
        assertEquals(BadRequestPlaylistException.class, exception.getClass());
    }
}