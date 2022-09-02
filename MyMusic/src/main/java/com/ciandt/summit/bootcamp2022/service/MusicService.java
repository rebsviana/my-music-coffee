package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;

import java.util.List;
public interface MusicService {
    public List<MusicDto> getMusicByNameOrArtist(String name);
    public  MusicDto getMusicById(String id);
}
