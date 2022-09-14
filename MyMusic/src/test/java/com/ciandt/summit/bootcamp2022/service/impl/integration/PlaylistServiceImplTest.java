package com.ciandt.summit.bootcamp2022.service.impl.integration;

import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistException;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.service.impl.PlaylistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID_NONEXISTENT;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_ID_NONEXISTENT_IN_PLAYLIST;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID;
import static com.ciandt.summit.bootcamp2022.config.Factory.PLAYLIST_ID_NONEXISTENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PlaylistServiceImplTest {

    @Autowired
    private PlaylistServiceImpl playlistService;
    @Autowired
    private PlaylistsRepository repository;

    @BeforeEach
    void setup(){

    }

    @Test
    @DisplayName("When delete music in playlist by id then deleted music")
    void delete() {
        playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID);

        assertFalse(repository.findById(PLAYLIST_ID).get().getMusics().contains(MUSIC_ID));
    }

    @Test
    @DisplayName("When delete music in playlist and playlist id doesn't exist then PlaylistDoesntExistException")
    void deleteWhenPlaylistDoesntExist() {
       var exception = assertThrows(PlaylistDoesntExistException.class,
               () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID, PLAYLIST_ID_NONEXISTENT));

        assertEquals(PlaylistDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When delete music in playlist and music id doesn't exist then MusicDoesntExistException")
    void deleteWhenMusicDoesntExist() {
        var exception = assertThrows(MusicDoesntExistException.class,
                () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID_NONEXISTENT, PLAYLIST_ID));

        assertEquals(MusicDoesntExistException.class, exception.getClass());
    }

    @Test
    @DisplayName("When delete music in playlist and music doesn't exist in playlist then MusicDoesntExistInPlaylistException")
    void deleteWhenMusicInPlaylistDoesntExist() {
        var exception = assertThrows(MusicDoesntExistInPlaylistException.class,
                () -> playlistService.deleteMusicFromPlaylist(MUSIC_ID_NONEXISTENT_IN_PLAYLIST, PLAYLIST_ID));

        assertEquals(MusicDoesntExistInPlaylistException.class, exception.getClass());
    }
}