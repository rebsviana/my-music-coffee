package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.exceptions.BadRequestPlaylistException;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlaylistServiceImplTest {

    @Mock
    private MusicServiceImpl musicService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private PlaylistsRepository playlistsRepository;

    @InjectMocks
    private PlaylistServiceImpl playlistService;
    private Playlist playlist;
    private PlaylistDto playlistDto;
    private static final String PLAYLIST_ID = "123456";
    public static final String ID_NOT_EXIST = "789456";

    @BeforeEach
    void setup(){
        playlist = new Playlist(PLAYLIST_ID);
        playlistDto = new PlaylistDto(PLAYLIST_ID);
    }

    @Test
    @DisplayName("When get playlist by id then return a playlist")
    void whenGetPlaylistByIdThenReturnAPlaylist() {
        when(playlistsRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(playlist));

        when(mapper.convertValue(playlist, PlaylistDto.class)).thenReturn(playlistDto);

        var response = playlistService.getPlaylistById(PLAYLIST_ID);

        assertNotNull(response);
        assertEquals(playlistDto, response);
        assertEquals(PlaylistDto.class, response.getClass());
        assertEquals(PLAYLIST_ID, response.getId());
        verify(playlistsRepository, times(1)).findById(PLAYLIST_ID);
        verify(mapper, times(1)).convertValue(playlist, PlaylistDto.class);
    }

    @Test
    @DisplayName("When get playlist by id and playlist doesn't exist then return BadRequestPlaylistException")
    void whenGetPlaylistByIdAndPlaylistDoesntExistThenReturnBadRequestPlaylistException() {
        when(playlistsRepository.findById(anyString()))
                .thenThrow(new BadRequestPlaylistException("Playlist doesn't exist"));

        var exception = assertThrows(BadRequestPlaylistException.class,
                () -> playlistService.getPlaylistById(ID_NOT_EXIST));

        assertNotNull(exception);
        assertEquals(BadRequestPlaylistException.class, exception.getClass());
        assertEquals("Playlist doesn't exist", exception.getMessage());
        verify(playlistsRepository, times(1)).findById(ID_NOT_EXIST);
        verify(mapper, times(0)).convertValue(playlist, PlaylistDto.class);
    }

}