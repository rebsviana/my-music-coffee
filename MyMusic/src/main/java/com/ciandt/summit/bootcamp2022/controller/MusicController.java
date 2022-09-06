package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.TokenAuthorizerService;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.MusicServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class MusicController {

    @Autowired
    private MusicServiceImpl musicService;

    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;

    @ApiOperation(value = "Get some music with filter", notes = "Returns a list of music")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = NoContentException.MESSAGE, response = Object.class),
            @ApiResponse(code = 400, message = MinLengthRequiredException.MESSAGE),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @GetMapping
    public ResponseEntity<List<MusicDto>> getMusicByNameOrArtistWithFilter(@RequestParam("filtro") String filterName,
                                                                           @RequestHeader(value="name") String userName,
                                                                           @RequestHeader(value="token") String userToken){
        log.info("Starting the route search new music with filter " + filterName);
        tokenAuthorizerService.verifyTokenAuthorizer(userName, userToken);
        log.info("Authorized user:" + userName);
        var listMusicDto = musicService.getMusicByNameOrArtist(filterName);

        return ResponseEntity.ok(listMusicDto);
    }
}