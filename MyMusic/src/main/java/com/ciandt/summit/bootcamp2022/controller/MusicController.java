package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.TokenAuthorizerService;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.MusicServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/music", produces = "application/json")
public class MusicController {

    @Autowired
    private MusicServiceImpl musicService;

    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;

    @ApiOperation(value = "Get some music with filter", notes = "Returns a list of music")
    @GetMapping
    public ResponseEntity<List<MusicDto>> getMusicByNameOrArtistWithFilter(@RequestParam("filtro") String filterName,
                                                                           @RequestHeader(value="name") String name,
                                                                           @RequestHeader(value="token") String token){
        tokenAuthorizerService.verifyTokenAuthorizer(name, token);
        var listMusicDto = musicService.getMusicByNameOrArtist(filterName);

        return ResponseEntity.ok(listMusicDto);
    }
}