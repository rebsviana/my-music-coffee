package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;

public interface MusicService {
    public PageDecoratorDto<MusicDto> getMusicByNameOrArtist(String name);
    public  MusicDto getMusicById(String id);
}
