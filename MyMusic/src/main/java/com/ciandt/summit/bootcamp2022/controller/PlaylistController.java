package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.PlaylistServiceImpl;
import com.ciandt.summit.bootcamp2022.service.serviceImpl.TokenAuthorizerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/playlists", produces = "application/json")
public class PlaylistController {

    @Autowired
    private PlaylistServiceImpl playlistService;
    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;

    @ApiOperation(value = "Save music in playlist", notes = "Saved new music in playlist")
    @PostMapping("/{playlistId}/musicas")
    public ResponseEntity<Void> saveMusicInPlaylist (@RequestBody MusicDto musicDto,
                                                     @PathVariable("playlistId") String playlistId,
                                                     @RequestHeader(value="name") String name,
                                                     @RequestHeader(value="token") String token){
        tokenAuthorizerService.verifyTokenAuthorizer(name, token);
        var savedPlaylist = playlistService.saveMusicInPlaylist(musicDto, playlistId);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPlaylist.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
