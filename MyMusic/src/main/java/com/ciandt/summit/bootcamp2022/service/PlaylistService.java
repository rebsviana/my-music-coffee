package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;

public interface PlaylistService {
    public PlaylistDto getPlaylistById(String id);

    public PlaylistDto saveMusicInPlaylist(MusicDto musicDto, String playlistId, String userId);

    public void deleteMusicFromPlaylist(String musicId, String playlistId);
}
