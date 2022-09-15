package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.enums.UserType;
import com.ciandt.summit.bootcamp2022.exceptions.MaxMusicCapacityForFreeUserException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicAlreadyExistsInThisPlaylistException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistOnThisUserException;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.Optional;

import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID_NONEXISTENT;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private PlaylistServiceImpl playlistService;

    private Playlist playlist;

    private Playlist playlistWithMusics;
    private Playlist playlistWithOneMusicNonExistent;
    private MusicDto musicDto;
    private Music music;
    private UserDto userDto;
    private Playlist playlistWithOneMusic;
    private MusicDto sameMusicToSave;

    @BeforeEach
    void setup(){
        playlistWithOneMusicNonExistent = Factory.createPlaylistWithOneMusicNonExistent();
        playlist = Factory.createPlaylist();
        playlistWithMusics = Factory.createPlaylistWithMusics();
        musicDto = Factory.createMusicDto();
        music = Factory.createMusic();
        userDto = Factory.createUserDto();
        playlistWithOneMusic = Factory.createPlaylistWithOneMusic();
        sameMusicToSave = Factory.createMusicDto();
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
                () -> playlistService.getPlaylistById(PLAYLIST_ID_NONEXISTENT));

        assertNotNull(exception);
        assertEquals(PlaylistDoesntExistException.class, exception.getClass());
        assertEquals(PlaylistDoesntExistException.MESSAGE, exception.getMessage());
        verify(playlistsRepository, times(1)).findById(PLAYLIST_ID_NONEXISTENT);
    }

    @Test
    @DisplayName("When user common save music in playlist then add music to the playlist")
    void whenUserCommonSaveMusicInPlaylistThenAddMusicToThePlaylist() {
        when(playlistsRepository.findById(anyString())).thenReturn(Optional.of(playlist));

        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);

        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);

        var response = playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID, USER_NICKNAME);

        assertNotNull(response);
        assertEquals(PlaylistDto.class, response.getClass());
        assertEquals(PLAYLIST_ID, response.getId());
        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).save(any());
        verify(userServiceImpl, times(1)).getUserByNickname(anyString());
    }

    @Test
    @DisplayName("When user premium save music in playlist then add music to the playlist")
    void whenUserPremiumSaveMusicInPlaylistThenAddMusicToThePlaylist() {
        userDto.setUserType(UserType.PREMIUM);

        when(playlistsRepository.findById(anyString())).thenReturn(Optional.of(playlist));

        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);

        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);

        var response = playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID, USER_NICKNAME);

        assertNotNull(response);
        assertEquals(PlaylistDto.class, response.getClass());
        assertEquals(PLAYLIST_ID, response.getId());
        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).save(any());
        verify(userServiceImpl, times(1)).getUserByNickname(anyString());
    }

    @Test
    @DisplayName("When save music in playlist and playlist doesnt exist on this user then PlaylistDoesntExistOnThisUserException")
    void whenSaveMusicInPlaylistThenPlaylistDoesntExistOnThisUserException() {
        when(playlistsRepository.findById(anyString())).thenReturn(Optional.of(playlist));

        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);

        var response = assertThrows(PlaylistDoesntExistOnThisUserException.class,
                () ->playlistService.saveMusicInPlaylist(musicDto, "798456132", USER_NICKNAME));

        assertNotNull(response);
        assertEquals(PlaylistDoesntExistOnThisUserException.class, response.getClass());
        assertEquals(PlaylistDoesntExistOnThisUserException.MESSAGE, response.getMessage());
        verify(musicServiceImpl, times(0)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(0)).save(any());
        verify(userServiceImpl, times(1)).getUserByNickname(anyString());
        verify(playlistsRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("When a free user save the sixth music in the playlist then return MaxMusicCapacityForFreeUserException")
    void whenSaveMusicInPlaylistThenMaxMusicCapacityForFreeUserException() {
        userDto.setPlaylistId(PlaylistDto.builder()
                .id(playlistWithMusics.getId())
                .musics(playlistWithMusics.getMusics())
                .build());

        when(playlistsRepository.findById(anyString())).thenReturn(Optional.of(playlistWithMusics));

        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);

        var response = assertThrows(MaxMusicCapacityForFreeUserException.class,
                () -> playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID, USER_NICKNAME));

        assertNotNull(response);
        assertEquals(MaxMusicCapacityForFreeUserException.class, response.getClass());
        assertEquals(MaxMusicCapacityForFreeUserException.MESSAGE, response.getMessage());
        verify(musicServiceImpl, times(0)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(0)).save(any());
        verify(userServiceImpl, times(1)).getUserByNickname(anyString());
        verify(playlistsRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("When user save the music and music already exist in this playlist then return MusicAlreadyExistsInThisPlaylistException")
    void whenSaveMusicInPlaylistThenMusicAlreadyExistsInThisPlaylistException() {
        userDto.setPlaylistId(PlaylistDto.builder()
                .id(playlistWithOneMusic.getId())
                .musics(playlistWithOneMusic.getMusics())
                .build());

        when(playlistsRepository.findById(anyString())).thenReturn(Optional.of(playlistWithOneMusic));

        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);

        var response = assertThrows(MusicAlreadyExistsInThisPlaylistException.class,
                () -> playlistService.saveMusicInPlaylist(sameMusicToSave, PLAYLIST_ID, USER_NICKNAME));

        assertNotNull(response);
        assertEquals(MusicAlreadyExistsInThisPlaylistException.class, response.getClass());
        assertEquals(MusicAlreadyExistsInThisPlaylistException.MESSAGE, response.getMessage());
        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(0)).save(any());
        verify(userServiceImpl, times(1)).getUserByNickname(anyString());
        verify(playlistsRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("When delete music from playlist then remove it from playlist")
    void whenDeleteMusicFromPlaylistThenRemoveItFromPlaylistThenUpdate() {
        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);
        when(playlistsRepository.findById(anyString())).thenReturn(Optional.ofNullable(playlist));
        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);
        playlist.getMusics().add(music);

        var message = playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID, USER_NICKNAME);

        assertEquals(Factory.MSG_200_MUSIC_DELETE_SUCCESSFULLY, message);
        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).findById(playlist.getId());
        verify(playlistsRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("When delete music from playlist and music doesn't exist then return MusicDoesntExistInPlaylistException")
    void whenDeleteMusicFromPlaylistAndMusicDoesntExistThenReturnMusicDoesntExistInPlaylistException() {
        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);
        when(playlistsRepository.findById(anyString())).thenReturn(Optional.ofNullable(playlistWithOneMusicNonExistent));
        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);

        var exception = assertThrows(MusicDoesntExistInPlaylistException.class,
                () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID, USER_NICKNAME));

        assertNotNull(exception);
        assertEquals(MusicDoesntExistInPlaylistException.class, exception.getClass());
        assertEquals(MusicDoesntExistInPlaylistException.MESSAGE, exception.getMessage());

        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).findById(playlist.getId());
        verify(playlistsRepository, times(0)).save(playlist);
    }

    @Test
    @DisplayName("When delete music from playlist and playlist doesn't exist on this user then return PlaylistDoesntExistOnThisUserException")
    void whenDeleteMusicFromPlaylistAndPlaylistDoesntExistOnThisUserThenPlaylistDoesntExistOnThisUserException() {
        when(musicServiceImpl.getMusicById(anyString())).thenReturn(musicDto);
        when(playlistsRepository.findById(anyString())).thenReturn(Optional.ofNullable(playlist));
        when(userServiceImpl.getUserByNickname(anyString())).thenReturn(userDto);

        userDto.setPlaylistId(PlaylistDto.builder().id("12345").build());

        var exception = assertThrows(PlaylistDoesntExistOnThisUserException.class,
                () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID, USER_NICKNAME));

        assertNotNull(exception);
        assertEquals(PlaylistDoesntExistOnThisUserException.class, exception.getClass());
        assertEquals(PlaylistDoesntExistOnThisUserException.MESSAGE, exception.getMessage());

        verify(musicServiceImpl, times(1)).getMusicById(musicDto.getId());
        verify(playlistsRepository, times(1)).findById(playlist.getId());
        verify(playlistsRepository, times(0)).save(playlist);
    }
}