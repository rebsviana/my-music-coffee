package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MusicService {
    @Cacheable(value= "cacheSearchMusic")
    public List<MusicDto> getMusicByNameOrArtist(String name) throws InterruptedException;
    public  MusicDto getMusicById(String id);
}
