package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.exceptions.BadRequestPlaylistException;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
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
    private MusicServiceImpl musicServiceImpl;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private PlaylistsRepository playlistsRepository;

    @InjectMocks
    private PlaylistServiceImpl playlistService;
    private Playlist playlist;
    private PlaylistDto playlistDto;
    private MusicDto musicDto;
    private Music music;
    private static final String PLAYLIST_ID = "123456";
    public static final String ID_NOT_EXIST = "789456";
    public static final String MUSIC_ID = "12343";
    public static final String MUSIC_NAME = "Harley";
    public static final String ARTIST_ID = "1344";
    public static final String ARTIST_NAME = "David";

    @BeforeEach
    void setup(){
        playlist = new Playlist(PLAYLIST_ID);
        playlistDto = new PlaylistDto(PLAYLIST_ID);
        musicDto = new MusicDto(MUSIC_ID, MUSIC_NAME, new ArtistDto(ARTIST_ID, ARTIST_NAME));
        music = new Music(MUSIC_ID, MUSIC_NAME, new Artist(ARTIST_ID, ARTIST_NAME));
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

    @Test
    @DisplayName("When save music in playlist then add music to the playlist")
    void whenSaveMusicInPlaylistThenAddMusicToThePlaylist() {
        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);

        when(mapper.convertValue(musicDto, Music.class)).thenReturn(music);

        when(playlistsRepository.findById(anyString())).thenReturn(Optional.ofNullable(playlist));

        when(playlistService.getPlaylistById(anyString())).thenReturn(playlistDto);

        when(mapper.convertValue(playlistDto, Playlist.class)).thenReturn(playlist);

        when(mapper.convertValue(playlist, PlaylistDto.class)).thenReturn(playlistDto);

        when(playlistsRepository.save(playlist)).thenReturn(playlist);

        var response = playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID);

        assertNotNull(response);
        assertEquals(playlistDto, response);
        assertEquals(PlaylistDto.class, response.getClass());
        assertEquals(PLAYLIST_ID, response.getId());
        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).save(playlist);
        verify(mapper, times(1)).convertValue(musicDto, Music.class);
        verify(mapper, times(2)).convertValue(playlist, PlaylistDto.class);
        verify(mapper, times(1)).convertValue(playlistDto, Playlist.class);
    }
}