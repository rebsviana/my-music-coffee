package com.ciandt.summit.bootcamp2022.config;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;

import java.util.HashSet;

public class Factory {
    public static final String MUSIC_ID = "12343";
    public static final String MUSIC_NAME = "Harley";
    public static final String ARTIST_ID = "1344";
    public static final String ARTIST_NAME = "David";
    public static final String PLAYLIST_ID = "123456";
    public static final String NAME_TOKEN = "Bruno";
    public static final String TOKEN = "123456789";
    public static final String MSG_500 = "Internal Error";
    public static final String MSG_200_OK = "OK";
    public static final String MSG_200_DELETE_SUCCESSFULLY = "Music deleted successfully";
    public static final String MSG_201_CREATED = "Created";
    public static final String MSG_400_MUSIC_DOESNT_EXIST = "Music doesn't exist";
    public static final String MSG_400_PLAYLIST_DOESNT_EXIST = "Playlist doesn't exist";
    public static final String MSG_400_MUSIC_DOESNT_EXIST_IN_PLAYLIST = "Music doesn't exist in playlist";

    public static Artist createArtist() { return new Artist(ARTIST_ID, ARTIST_NAME); }
    public static Music createMusic(){ return new Music(MUSIC_ID, MUSIC_NAME, createArtist()); }
    public static ArtistDto createArtistDto() { return new ArtistDto(ARTIST_ID, ARTIST_NAME); }
    public static MusicDto createMusicDto() { return new MusicDto(MUSIC_ID, MUSIC_NAME, createArtistDto()); }
    public static Playlist createPlaylist() { return new Playlist(PLAYLIST_ID, new HashSet<Music>());}
    public static PlaylistDto createPlaylistDto() { return new PlaylistDto(PLAYLIST_ID, new HashSet<Music>()); }

    public static final String MESSAGE_BAD_REQUEST_PAYLOAD = "Payload body incorrect: id of music is null";
}
