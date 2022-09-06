package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicDoesntExistException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;

    @Override
    public Page<MusicDto> getMusicByNameOrArtist(String name, Pageable pageable) {
        checkNotNull(name, "Name cannot be null");

        if (name.length() < 3)
            throw new MinLengthRequiredException();

        var musicEntity = musicRepository.findMusicByNameOrArtist(name, pageable);

        if (musicEntity.isEmpty())
            throw new NoContentException();

        var musicDto = musicEntity
                .map(music -> MusicDto.builder()
                        .artistId(ArtistDto.builder()
                                .id(music.getArtistId().getId())
                                .name(music.getArtistId().getName())
                                .build())
                        .id(music.getId())
                        .name(music.getName())
                        .build());

        return musicDto;
    }

    @Override
    public MusicDto getMusicById(String id) {
        checkNotNull(id, "Music cannot be null");

        var musicEntity = musicRepository.findById(id)
                .orElseThrow(MusicDoesntExistException::new);

        return MusicDto.builder()
                .name(musicEntity.getName())
                .id(musicEntity.getId())
                .artistId(ArtistDto.builder()
                        .id(musicEntity.getArtistId().getId())
                        .name(musicEntity.getArtistId().getName())
                        .build()
                ).build();
    }
}
