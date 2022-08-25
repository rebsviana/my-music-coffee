package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.service.MusicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;


@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;
    private final ObjectMapper mapper;

    @Override
    public List<MusicDto> getMusicByNameOrArtist(String name) {
        checkNotNull(name, "Name can not be null");

        if (name.length() < 3)
            throw new MinLengthRequiredException();

        var musicEntity = musicRepository.findMusicByNameOrArtist(name);

        if (musicEntity.isEmpty())
            throw new NoContentException();

        return musicEntity.stream()
                .map(music -> mapper.convertValue(music, MusicDto.class))
                .collect(Collectors.toList());
    }
}
