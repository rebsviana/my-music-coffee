package com.ciandt.summit.bootcamp2022.service.impl.integration;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.service.impl.MusicServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ciandt.summit.bootcamp2022.config.Factory.ARTIST_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.ARTIST_NAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID_NONEXISTENT;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_NAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_NAME_MIN_LENGTH;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_NAME_NO_CONTENT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MusicServiceImplTest {

    @Autowired
    private MusicServiceImpl musicServiceImpl;

    @Test
    @DisplayName("When get music by id then return musicDto")
    void whenGetMusicByIdThenReturnMusicDto() {
        var musicDto = musicServiceImpl.getMusicById(MUSIC_ID);

        assertNotNull(musicDto);
        assertEquals(MusicDto.class, musicDto.getClass());
        assertEquals(MUSIC_ID, musicDto.getId());
        assertEquals(MUSIC_NAME, musicDto.getName());
        assertEquals(ARTIST_ID, musicDto.getArtistId().getId());
        assertEquals(ARTIST_NAME, musicDto.getArtistId().getName());
    }

    @Test
    @DisplayName("When get music by id then return MusicDoesntExistException")
    void whenGetMusicByIdThenReturnMusicDoesntExistException() {
        var exception = assertThrows(MusicDoesntExistException.class,
                () -> musicServiceImpl.getMusicById(MUSIC_ID_NONEXISTENT));

        assertNotNull(exception);
        assertEquals(MusicDoesntExistException.MESSAGE, exception.getMessage());
        assertEquals(MusicDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When get music or artist by name then return PageDecoratorDto")
    void whenGetMusicOrArtistByNameThenReturnPageDecoratorDto() {
      var result = musicServiceImpl.getMusicByNameOrArtist(MUSIC_NAME);

        assertThat(result.getContent().get(0).getName(), containsString(MUSIC_NAME));
    }

    @Test
    @DisplayName("When get music or artist by name then return MinLengthRequiredException")
    void whenGetMusicOrArtistByNameThenReturnMinLengthRequiredException() {
        var exception = assertThrows(MinLengthRequiredException.class,
                () -> musicServiceImpl.getMusicByNameOrArtist(MUSIC_NAME_MIN_LENGTH));

        assertNotNull(exception);
        assertEquals(MinLengthRequiredException.MESSAGE, exception.getMessage());
        assertEquals(MinLengthRequiredException.class, exception.getClass());
    }

    @Test
    @DisplayName("When get music or artist by name then return NoContentException")
    void whenGetMusicOrArtistByNameThenReturnNoContentException() {
        var exception = assertThrows(NoContentException.class,
                () -> musicServiceImpl.getMusicByNameOrArtist(MUSIC_NAME_NO_CONTENT));

        assertNotNull(exception);
        assertEquals(NoContentException.MESSAGE, exception.getMessage());
        assertEquals(NoContentException.class, exception.getClass());
    }
}