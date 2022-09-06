package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static com.ciandt.summit.bootcamp2022.tests.Factory.ARTIST_ID;
import static com.ciandt.summit.bootcamp2022.tests.Factory.ARTIST_NAME;
import static com.ciandt.summit.bootcamp2022.tests.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.tests.Factory.MUSIC_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MusicServiceImplTest {
    @Mock
    private MusicRepository musicRepository;
    @InjectMocks
    private MusicServiceImpl service;
    private Music music;

    @BeforeEach
    void setup() {
        music = Factory.createMusic();
    }

    @Test
    @DisplayName("When find music by name or artist then return list of MusicDto")
    void whenFindMusicByNameOrArtistThenReturnListOfMusicDto() {
        when(musicRepository.findMusicByNameOrArtist(anyString()))
                .thenReturn(List.of(music));

        var list = service.getMusicByNameOrArtist("Harley");

        assertThat(list.get(0).getName(), containsString("Harley"));
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
    @DisplayName("When find music by id and music doesn't exist then return MusicDoesntExistException")
    void whenGetMusicByIdAndMusicDoesntExistThenReturnMusicDoesntExistException() {
        when(musicRepository.findById(anyString()))
                .thenThrow(new MusicDoesntExistException());

        var exception = assertThrows(MusicDoesntExistException.class,
                () -> service.getMusicById(MUSIC_ID));

        assertNotNull(exception);
        assertEquals(MusicDoesntExistException.MESSAGE, exception.getMessage());
        assertEquals(MusicDoesntExistException.class, exception.getClass());
    }
}