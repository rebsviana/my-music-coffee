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
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistsRepository playlistsRepository;
    @Autowired
    private MusicServiceImpl musicService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public PlaylistDto getPlaylistById(String id) {
        checkNotNull(id,"Playlist cannot be null");

        var playlistEntity = playlistsRepository.findById(id)
                .orElseThrow(PlaylistDoesntExistException::new);

        return PlaylistDto.builder()
                .id(playlistEntity.getId())
                .musics(playlistEntity.getMusics())
                .build();
    }

    @Override
    public PlaylistDto saveMusicInPlaylist(MusicDto musicDto, String playlistId, String nickname) {
        checkNotNull(playlistId,"Playlist cannot be null");
        checkNotNull(nickname,"Nickname cannot be null");
        checkNotNull(musicDto.getId(),"Payload body incorrect: id of music is null");
        checkNotNull(musicDto.getName(),"Payload body incorrect: name of music is null");
        checkNotNull(musicDto.getArtistId().getId(),"Payload body incorrect: id of artist is null");
        checkNotNull(musicDto.getArtistId().getName(),"Payload body incorrect: name of artist is null");

        var playlistDto = getPlaylistById(playlistId);

        var user = getUserDto(playlistId, nickname);

        if (user.getUserType().equals(UserType.COMMON) && user.getPlaylistId().getMusics().size() >= 5)
            throw new MaxMusicCapacityForFreeUserException();

        musicService.getMusicById(musicDto.getId());
        Music music = Music.builder()
                .name(musicDto.getName())
                .id(musicDto.getId())
                .artistId(Artist.builder()
                        .id(musicDto.getArtistId().getId())
                        .name(musicDto.getArtistId().getName())
                        .build()
                ).build();

        var playlist = Playlist.builder()
                .id(playlistDto.getId())
                .musics(playlistDto.getMusics())
                .build();

        if (playlist.getMusics()
                .stream()
                .filter(m -> m.getId().equals(music.getId()))
                .findFirst()
                .isEmpty()) {
            playlist.getMusics().add(music);
            playlistsRepository.save(playlist);
        }
        else throw new MusicAlreadyExistsInThisPlaylistException();

        return PlaylistDto.builder()
                .id(playlist.getId())
                .musics(playlist.getMusics())
                .build();
    }

    @Override
    public String deleteMusicFromPlaylist(String musicId, String playlistId, String nickname) {
        var musicDto = musicService.getMusicById(musicId);
        Music music = Music.builder()
                .name(musicDto.getName())
                .id(musicDto.getId())
                .artistId(Artist.builder()
                        .id(musicDto.getArtistId().getId())
                        .name(musicDto.getArtistId().getName())
                        .build()
                ).build();

        var playlistDto = getPlaylistById(playlistId);

        getUserDto(playlistId, nickname);

        var playlist = Playlist.builder()
                        .id(playlistDto.getId())
                        .musics(playlistDto.getMusics())
                        .build();

        var musicInPlaylist = playlist.getMusics()
                .stream()
                .filter(musicObject -> Objects.equals(musicObject.getId(), music.getId()))
                .findFirst()
                .orElseThrow(MusicDoesntExistInPlaylistException::new);

        playlist.getMusics().remove(musicInPlaylist);
        playlistsRepository.save(playlist);
        return Factory.MSG_200_MUSIC_DELETE_SUCCESSFULLY;
    }

    private UserDto getUserDto(String playlistId, String nickname) {
        var user = userService.getUserByNickname(nickname);

        if (!Objects.equals(user.getPlaylistId().getId(), playlistId))
            throw new PlaylistDoesntExistOnThisUserException();

        return user;
    }
}
