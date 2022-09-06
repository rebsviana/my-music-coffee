package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistException;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static com.ciandt.summit.bootcamp2022.tests.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.tests.Factory.PLAYLIST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlaylistServiceImplTest {
    @Mock
    private MusicServiceImpl musicServiceImpl;

    @Mock
    private PlaylistsRepository playlistsRepository;

    @InjectMocks
    private PlaylistServiceImpl playlistService;
    private Playlist playlist;
    private MusicDto musicDto;
    private Music music;

    public static final String ID_NOT_EXIST = "789456";

    @BeforeEach
    void setup(){
        playlist = Factory.createPlaylist();
        musicDto = Factory.createMusicDto();
        music = Factory.createMusic();
    }

    @Test
    @DisplayName("When get playlist by id then return a playlist")
    void whenGetPlaylistByIdThenReturnAPlaylist() {
        when(playlistsRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(playlist));

        var response = playlistService.getPlaylistById(PLAYLIST_ID);

        assertNotNull(response);
        assertEquals(PlaylistDto.class, response.getClass());
        assertEquals(PLAYLIST_ID, response.getId());
        verify(playlistsRepository, times(1)).findById(PLAYLIST_ID);
    }

    @Test
    @DisplayName("When get playlist by id and playlist doesn't exist then return PlaylistDoesntExistException")
    void whenGetPlaylistByIdAndPlaylistDoesntExistThenReturnPlaylistDoesntExistException() {
        when(playlistsRepository.findById(anyString()))
                .thenThrow(new PlaylistDoesntExistException());

        var exception = assertThrows(PlaylistDoesntExistException.class,
                () -> playlistService.getPlaylistById(ID_NOT_EXIST));

        assertNotNull(exception);
        assertEquals(PlaylistDoesntExistException.class, exception.getClass());
        assertEquals(PlaylistDoesntExistException.MESSAGE, exception.getMessage());
        verify(playlistsRepository, times(1)).findById(ID_NOT_EXIST);
    }

    @Test
    @DisplayName("When save music in playlist then add music to the playlist")
    void whenSaveMusicInPlaylistThenAddMusicToThePlaylist() {
        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);

        when(playlistsRepository.findById(anyString())).thenReturn(Optional.of(playlist));

        var response = playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID);

        assertNotNull(response);
        assertEquals(PlaylistDto.class, response.getClass());
        assertEquals(PLAYLIST_ID, response.getId());
        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("When delete music from playlist then remove it from playlist")
    void whenDeleteMusicFromPlaylistThenRemoveItFromPlaylistThenUpdate() {
        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);
        when(playlistsRepository.findById(anyString())).thenReturn(Optional.ofNullable(playlist));
        playlist.getMusics().add(music);

        playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID);

        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).findById(playlist.getId());
        verify(playlistsRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("When delete music from playlist and music doesn't exist then return MusicDoesntExistInPlaylistException")
    void whenDeleteMusicFromPlaylistAndMusicDoesntExistThenReturnMusicDoesntExistInPlaylistException() {
        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);
        when(playlistsRepository.findById(anyString())).thenReturn(Optional.ofNullable(playlist));

        var exception = assertThrows(MusicDoesntExistInPlaylistException.class,
                () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID));

        assertNotNull(exception);
        assertEquals(MusicDoesntExistInPlaylistException.class, exception.getClass());
        assertEquals(MusicDoesntExistInPlaylistException.MESSAGE, exception.getMessage());

        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).findById(playlist.getId());
        verify(playlistsRepository, times(0)).save(playlist);
    }
}