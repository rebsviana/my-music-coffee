package com.ciandt.summit.bootcamp2022.service.impl.integration;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.MaxMusicCapacityForFreeUserException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistOnThisUserException;
import com.ciandt.summit.bootcamp2022.exceptions.UserDoesntExistException;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.service.impl.PlaylistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ciandt.summit.bootcamp2022.config.Factory.MESSAGE_BAD_REQUEST_PAYLOAD;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID_NONEXISTENT;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID_NONEXISTENT_IN_PLAYLIST;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID_NONEXISTENT;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID_NONEXISTENT_TO_USER;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID_USER_MAX_CAPACITY;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME_MAX_CAPACITY;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME_NONEXISTENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PlaylistServiceImplTest {

    @Autowired
    private PlaylistServiceImpl playlistService;
    @Autowired
    private PlaylistsRepository repository;

    private MusicDto musicDto;
    private MusicDto musicDtoIncompleteBody;

    @BeforeEach
    void setup(){
        musicDto = Factory.createMusicDto();
        musicDtoIncompleteBody = Factory.createMusicDtoWithIncompleteBody();
    }

    @Test
    @DisplayName("When save music in playlist by id then return PlaylistDto")
    void whenSaveMusicInPlaylistThenReturnPlaylistDto() {
        var playlistSaved = playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID, USER_NICKNAME);

        assertEquals(PLAYLIST_ID, playlistSaved.getId());
        assertEquals(1, playlistSaved.getMusics().size());
    }

    @Test
    @DisplayName("When ave music in playlist and playlist id doesnt exist then return PlaylistDoesntExistException")
    void whenSaveMusicInPlaylistThenPlaylistDoesntExistException() {
        var exception = assertThrows(PlaylistDoesntExistException.class,
                () -> playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID_NONEXISTENT, USER_NICKNAME));

        assertEquals(PlaylistDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When ave music in playlist and user doesnt exist then return UserDoesntExistException")
    void whenSaveMusicInPlaylistThenUserDoesntExistException() {
        var exception = assertThrows(UserDoesntExistException.class,
                () -> playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID, USER_NICKNAME_NONEXISTENT));

        assertEquals(UserDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("Save music in playlist when playlist id doesnt exist on this user then return PlaylistDoesntExistOnThisUserException")
    void whenSaveMusicInPlaylistThenPlaylistDoesntExistOnThisUserException() {
        var exception = assertThrows(PlaylistDoesntExistOnThisUserException.class,
                () -> playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID_NONEXISTENT_TO_USER, USER_NICKNAME));

        assertEquals(PlaylistDoesntExistOnThisUserException.class, exception.getClass());
    }

    @Test
    @DisplayName("When save music in playlist and playlist id doesnt exist on this user then return MaxMusicCapacityForFreeUserException")
    void whenSaveMusicInPlaylistThenMaxMusicCapacityForFreeUserException() {
        var exception = assertThrows(MaxMusicCapacityForFreeUserException.class,
                () -> playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID_USER_MAX_CAPACITY, USER_NICKNAME_MAX_CAPACITY));

        assertEquals(MaxMusicCapacityForFreeUserException.class, exception.getClass());
    }

    @Test
    @DisplayName("When save music in playlist and music id doesnt exist then return MusicDoesntExistException")
    void whenSaveMusicInPlaylistThenMusicDoesntExistException() {
        musicDto.setId("123456");
        var exception = assertThrows(MusicDoesntExistException.class,
                () -> playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID, USER_NICKNAME));

        assertEquals(MusicDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When save music in playlist and incomplete body then return NullPointerException")
    void whenSaveMusicInPlaylistThenNullPointerException() {
        var exception = assertThrows(NullPointerException.class,
                () -> playlistService.saveMusicInPlaylist(musicDtoIncompleteBody, PLAYLIST_ID, USER_NICKNAME));

        assertEquals(NullPointerException.class, exception.getClass());
        assertEquals(MESSAGE_BAD_REQUEST_PAYLOAD, exception.getMessage());
    }


    @Test
    @DisplayName("When delete music in playlist by id then deleted music")
    void whenDeleteMusicFromPlaylistThenDeletedMusic() {
        playlistService.saveMusicInPlaylist(musicDto, PLAYLIST_ID, USER_NICKNAME);

        playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID);

        assertFalse(repository.findById(PLAYLIST_ID).get().getMusics().contains(MUSIC_ID));
    }

    @Test
    @DisplayName("When delete music in playlist and playlist id doesn't exist then PlaylistDoesntExistException")
    void whenDeleteMusicFromPlaylistThenPlaylistDoesntExist() {
       var exception = assertThrows(PlaylistDoesntExistException.class,
               () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID_NONEXISTENT));

        assertEquals(PlaylistDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When delete music in playlist and music id doesn't exist then MusicDoesntExistException")
    void whenDeleteMusicFromPlaylistThenMusicDoesntExist() {
        var exception = assertThrows(MusicDoesntExistException.class,
                () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID_NONEXISTENT, PLAYLIST_ID));

        assertEquals(MusicDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When delete music in playlist and music doesn't exist in playlist then MusicDoesntExistInPlaylistException")
    void whenDeleteMusicFromPlaylistThenMusicInPlaylistDoesntExist() {
        var exception = assertThrows(MusicDoesntExistInPlaylistException.class,
                () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID_NONEXISTENT_IN_PLAYLIST, PLAYLIST_ID));

        assertEquals(MusicDoesntExistInPlaylistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When get playlist by id then return playlist dto")
    void whenGetPlaylistByIdThenReturnPlaylistDto() {
        var playlist = playlistService.getPlaylistById(PLAYLIST_ID);

        assertEquals(PLAYLIST_ID, playlist.getId());
    }

}