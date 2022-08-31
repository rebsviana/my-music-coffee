package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.exceptions.BadRequestPlaylistException;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repository.PlaylistsRepository;
import com.ciandt.summit.bootcamp2022.service.PlaylistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private PlaylistsRepository playlistsRepository;
    private MusicServiceImpl musicService;
    private ObjectMapper mapper;

    @Override
    public PlaylistDto getPlaylistById(String id) {

        checkNotNull(id,"Id cannot be null");

        var playlistEntity = playlistsRepository.findById(id).orElseThrow(() -> new BadRequestPlaylistException("Playlist doesn't exist"));
        return mapper.convertValue(playlistEntity, PlaylistDto.class);
    }

    @Override
    public void saveMusicInPlaylist(MusicDto musicDto, String playlistId) {

        checkNotNull(playlistId,"Playlist doesn't exist");

        checkNotNull(musicDto.getId(),"Payload body incorrect");
        checkNotNull(musicDto.getName(),"Payload body incorrect");
        checkNotNull(musicDto.getArtistDtoId().getId(),"Payload body incorrect");
        checkNotNull(musicDto.getArtistDtoId().getName(),"Payload body incorrect");

        Music music = mapper.convertValue(musicService.getMusicById(musicDto.getId()), Music.class);
        Playlist playlist = mapper.convertValue(getPlaylistById(playlistId), Playlist.class);

        playlist.getMusics().add(music);
        music.getPlaylists().add(playlist);

        playlistsRepository.save(playlist);
    }
}
