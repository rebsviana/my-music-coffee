package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistException;
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
    public PlaylistDto saveMusicInPlaylist(MusicDto musicDto, String playlistId) {
        checkNotNull(playlistId,"Playlist is null");
        checkNotNull(musicDto.getId(),"Payload body incorrect: id of music is null");
        checkNotNull(musicDto.getName(),"Payload body incorrect: name of music is null");
        checkNotNull(musicDto.getArtistId().getId(),"Payload body incorrect: id of artist is null");
        checkNotNull(musicDto.getArtistId().getName(),"Payload body incorrect: name of artist is null");

        musicService.getMusicById(musicDto.getId());
        Music music = Music.builder()
                .name(musicDto.getName())
                .id(musicDto.getId())
                .artistId(Artist.builder()
                        .id(musicDto.getArtistId().getId())
                        .name(musicDto.getArtistId().getName())
                        .build()
                ).build();

        var playlistDto = getPlaylistById(playlistId);
        var playlist = Playlist.builder()
                .id(playlistDto.getId())
                .musics(playlistDto.getMusics())
                .build();

        playlist.getMusics().add(music);

        playlistsRepository.save(playlist);

        return PlaylistDto.builder()
                .id(playlist.getId())
                .musics(playlist.getMusics())
                .build();
    }

    @Override
    public void deleteMusicFromPlaylist(String musicId, String playlistId) {
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
    }
}
