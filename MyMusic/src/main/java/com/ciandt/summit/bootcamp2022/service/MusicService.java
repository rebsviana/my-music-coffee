package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;
import org.springframework.cache.annotation.Cacheable;

public interface MusicService {
    @Cacheable(value= "searchMusic")
    public PageDecoratorDto<MusicDto> getMusicByNameOrArtist(String name) throws InterruptedException;
    public  MusicDto getMusicById(String id);
}
