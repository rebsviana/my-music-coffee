package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;
import com.ciandt.summit.bootcamp2022.model.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MusicService {
    public PageDecoratorDto<MusicDto> getMusicByNameOrArtist(String name);
    public  MusicDto getMusicById(String id);
}
