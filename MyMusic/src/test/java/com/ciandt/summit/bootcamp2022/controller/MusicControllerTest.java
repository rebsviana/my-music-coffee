package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.impl.MusicServiceImpl;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ciandt.summit.bootcamp2022.config.Factory.AUTHORIZATION_BAERER;
import static com.ciandt.summit.bootcamp2022.config.Factory.MUSIC_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MusicControllerTest {
    @InjectMocks
    private MusicController controller;
    @Mock
    private MusicServiceImpl musicService;
    @Mock
    private TokenAuthorizerService tokenAuthorizerService;
    @Mock
    private HttpServletRequest request;
    private MusicDto musicDto;

    @BeforeEach
    void setup() {
        musicDto = Factory.createMusicDto();
    }

    @Test
    @DisplayName("When search some music with filter then return response entity with list of MusicDto")
    void whenGetMusicByNameOrArtistWithFilterThenReturnReponseEntityWithListMusicDto() {
        ResponseEntity<String> responseEntity= new ResponseEntity<>(Factory.MSG_200_OK, HttpStatus.CREATED);

        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenReturn(responseEntity);

        when(musicService.getMusicByNameOrArtist(anyString())).thenReturn(new PageDecoratorDto<>(new PageImpl<>(List.of(musicDto))));

        var response = controller.getMusicByNameOrArtistWithFilter(MUSIC_NAME);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PageDecoratorDto.class, response.getBody().getClass());
    }

    @Test
    @DisplayName("When search some music with filter and token unauthorized then return UnauthorizedAccessException")
    void whenGetMusicByNameOrArtistWithFilterThenReturnUnauthorizedAccessException(){
        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenThrow(new UnauthorizedAccessException());

        when(request.getHeader("Authorization")).thenReturn(AUTHORIZATION_BAERER);

        var exception = assertThrows(UnauthorizedAccessException.class,
                () -> controller.getMusicByNameOrArtistWithFilter(MUSIC_NAME));

        assertNotNull(exception);
        assertEquals(UnauthorizedAccessException.MESSAGE, exception.getMessage());
        assertEquals(UnauthorizedAccessException.class, exception.getClass());
    }
}