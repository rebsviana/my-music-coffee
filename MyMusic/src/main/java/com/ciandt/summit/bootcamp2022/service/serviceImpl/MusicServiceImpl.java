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

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private MusicRepository musicRepository;
    private ObjectMapper mapper;

    @Override
    public List<MusicDto> searchMusicNameOrArtistName(String name) {
        if (name.length() < 3)
            throw new MinLengthRequiredException();

        var musicEntity = musicRepository.searchMusicNameOrArtistName(name);

        if (musicEntity.isEmpty())
            throw new NoContentException();

        return musicEntity.stream()
                .map(music -> mapper.convertValue(music, MusicDto.class))
                .collect(Collectors.toList());
    }
}
