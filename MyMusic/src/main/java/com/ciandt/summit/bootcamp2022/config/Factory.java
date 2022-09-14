package com.ciandt.summit.bootcamp2022.config;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.enums.UserType;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.model.User;
import lombok.Generated;

import java.util.Arrays;
import java.util.HashSet;

@Generated
public class Factory {
    public static final String USER_ID = "12347";
    public static final String USER_NAME = "Mariana";
    public static final String USER_NICKNAME = "marianan";
    public static final UserType USER_TYPE = UserType.COMMON;
    public static final String MUSIC_ID = "12659604-a4a1-4c4c-8a5f-29fff1ad2ac5";
    public static final String MUSIC_ID_NONEXISTENT = "789456";
    public static final String MUSIC_ID_NONEXISTENT_IN_PLAYLIST = "3a253ef0-81ea-4a87-80a6-1a80bbea9fe4";
    public static final String MUSIC_NAME = "Harley";
    public static final String ARTIST_ID = "1344";
    public static final String ARTIST_NAME = "David";
    public static final String PLAYLIST_ID = "a39926f4-6acb-4497-884f-d4e5296ef652";
    public static final String PLAYLIST_ID_NONEXISTENT = "789456";
    public static final String NAME_TOKEN = "Bruno";
    public static final String TOKEN = "123456789";
    public static final String MSG_500 = "Internal Error";
    public static final String MSG_200_OK = "OK";
    public static final String MSG_200_MUSIC_DELETE_SUCCESSFULLY = "Music deleted successfully";
    public static final String MSG_201_CREATED = "Created";
    public static final String MSG_400_MUSIC_DOESNT_EXIST = "Music doesn't exist";
    public static final String MSG_400_PLAYLIST_DOESNT_EXIST = "Playlist doesn't exist";
    public static final String MSG_400_MUSIC_DOESNT_EXIST_IN_PLAYLIST = "Music doesn't exist in playlist";
    public static final String MSG_400_USER_ALREADY_EXIST = "User already exists";
    public static final String MSG_200_USER_CREATED_SUCCESSFULLY = "User created successfully";
    public static final String MSG_400_USER_DOESNT_EXIST = "User doesn't exist";
    public static final String MSG_400_PLAYLIST_DOESNT_EXIST_ON_USER = "Playlist doesn't exist";
    public static final String MSG_400_MAX_MUSIC_CAPACITY_USER_COMMON = "Capacity of five musics in playlist reached for common user";

    public static Artist createArtist() { return new Artist(ARTIST_ID, ARTIST_NAME); }
    public static Music createMusic(){ return new Music(MUSIC_ID, MUSIC_NAME, createArtist()); }
    public static User createUser() { return new User(USER_ID, USER_NAME, USER_NICKNAME, USER_TYPE, createPlaylist()); }
    public static ArtistDto createArtistDto() { return new ArtistDto(ARTIST_ID, ARTIST_NAME); }
    public static MusicDto createMusicDto() { return new MusicDto(MUSIC_ID, MUSIC_NAME, createArtistDto()); }
    public static UserDto createUserDto() { return new UserDto(USER_ID, USER_NAME, USER_NICKNAME, USER_TYPE, createPlaylistDto()); }
    public static Playlist createPlaylist() { return new Playlist(PLAYLIST_ID, new HashSet<Music>());}
    public static Playlist createPlaylistWithMusics() { return new Playlist(PLAYLIST_ID, new HashSet<Music>(Arrays.asList(createMusic(), createMusic(), createMusic(), createMusic(), createMusic())));}
    public static PlaylistDto createPlaylistDto() { return new PlaylistDto(PLAYLIST_ID, new HashSet<Music>()); }
    public static final String MESSAGE_BAD_REQUEST_PAYLOAD = "Payload body incorrect: id of music is null";

    private Factory(){}
}
