package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.exceptions.BadRequestPlaylistException;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.service.PlaylistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        checkNotNull(id,"Id cannot be null");

        var playlistEntity = playlistsRepository.findById(id)
                .orElseThrow(() -> new BadRequestPlaylistException("Playlist doesn't exist"));
        return mapper.convertValue(playlistEntity, PlaylistDto.class);
    }

    @Override
    public PlaylistDto saveMusicInPlaylist(MusicDto musicDto, String playlistId) {
        checkNotNull(playlistId,"Playlist doesn't exist");
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
}
