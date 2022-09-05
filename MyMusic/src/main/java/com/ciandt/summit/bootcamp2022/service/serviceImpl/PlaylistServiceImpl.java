package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.PlaylistDoesntExistException;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.service.PlaylistService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper mapper;

    @Override
    public PlaylistDto getPlaylistById(String id) {
        checkNotNull(id,"Playlist cannot be null");

        var playlistEntity = playlistsRepository.findById(id)
                .orElseThrow(PlaylistDoesntExistException::new);
        return mapper.convertValue(playlistEntity, PlaylistDto.class);
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
        Playlist playlist = mapper.convertValue(getPlaylistById(playlistId), Playlist.class);

        playlist.getMusics().add(music);

        return mapper.convertValue(playlistsRepository.save(playlist), PlaylistDto.class);
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

        Playlist playlist = mapper.convertValue(getPlaylistById(playlistId), Playlist.class);

        if(playlist.getMusics().stream().filter(musicObject -> Objects.equals(musicObject.getId(), music.getId())).findFirst().isEmpty())
            throw new MusicDoesntExistException();

        playlist.getMusics().remove(music);
        playlistsRepository.save(playlist);
    }
}
