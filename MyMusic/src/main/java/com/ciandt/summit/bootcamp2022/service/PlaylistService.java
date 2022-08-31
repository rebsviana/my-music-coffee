package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;

public interface PlaylistService {
    public PlaylistDto getPlaylistById(String id);

    public void saveMusicInPlaylist(MusicDto musicDto, String playlistId);
}
