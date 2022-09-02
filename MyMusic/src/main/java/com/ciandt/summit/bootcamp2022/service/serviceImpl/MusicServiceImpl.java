package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.BadRequestPlaylistException;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;

    @Override
    public List<MusicDto> getMusicByNameOrArtist(String name) {
        checkNotNull(name, "Name cannot be null");

        if (name.length() < 3)
            throw new MinLengthRequiredException();

        var musicEntity = musicRepository.findMusicByNameOrArtist(name);

        if (musicEntity.isEmpty())
            throw new NoContentException();

        return musicEntity.stream()
                .map(music -> MusicDto.builder()
                        .artistId(ArtistDto.builder()
                                .id(music.getArtistId().getId())
                                .name(music.getArtistId().getName())
                                .build())
                        .id(music.getId())
                        .name(music.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public MusicDto getMusicById(String id) {
        checkNotNull(id, "Id cannot be null");

        var musicEntity = musicRepository.findById(id)
                .orElseThrow(() -> new BadRequestPlaylistException("Music doesn't exist"));

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
