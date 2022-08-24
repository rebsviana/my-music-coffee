package com.ciandt.summit.bootcamp2022.service.serviceImpl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class MusicServiceImplTest {
    @Mock
    private MusicRepository musicRepository;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private MusicServiceImpl service;

    private Music music;
    private MusicDto musicDto;
    private Artist artist;

    @BeforeEach
    void setup() {
        artist = new Artist("1344", "David");
        music = new Music("12343", "Harley", artist);
        musicDto = new MusicDto("12343", "Harley", artist);
    }

    @Test
    void whenSearchMusicNameOrArtistNameThenReturnListOfMusicDto() {
        Mockito.when(musicRepository.searchMusicNameOrArtistName(Mockito.anyString()))
                .thenReturn(List.of(music));

        Mockito.when(mapper.convertValue(music, MusicDto.class))
                .thenReturn(musicDto);

        var list = service.searchMusicNameOrArtistName("Harley");

        assertThat(list.get(0).getName(), containsString("Harley"));
        assertNotEquals(list.get(0).getArtistId().getName(), containsString("Harley"));

        //TODO: Buscar metodo para comparar se há pelo menos um dos dois
    }
}