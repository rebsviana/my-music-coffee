package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.TokenAuthorizerService;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.MusicServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/music", produces = "application/json")
@Log4j2
public class MusicController {

    @Autowired
    private MusicServiceImpl musicService;

    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;

    @ApiOperation(value = "Get some music with filter", notes = "Returns a list of music")
    @GetMapping
    public ResponseEntity<Page<MusicDto>> getMusicByNameOrArtistWithFilter(@RequestParam("filtro") String filterName,
                                                                        @RequestHeader(value="name") String userName,
                                                                        @RequestHeader(value="token") String userToken, Pageable pageable){
        log.info("Starting the route search new music with filter " + filterName);
        tokenAuthorizerService.verifyTokenAuthorizer(userName, userToken);
        log.info("Authorized user:" + userName);
        var pageMusicDto = musicService.getMusicByNameOrArtist(filterName, pageable);

        return ResponseEntity.ok(pageMusicDto);
    }
}