package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.service.impl.PlaylistServiceImpl;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/playlists", produces = "application/json")
@Log4j2
public class PlaylistController {

    @Autowired
    private PlaylistServiceImpl playlistService;
    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;

    @ApiOperation(value = "Save music in playlist", notes = "Saved new music in playlist")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Factory.MSG_201_CREATED),
            @ApiResponse(code = 400, message = Factory.MSG_400_MUSIC_DOESNT_EXIST + "<br/>" + Factory.MSG_400_PLAYLIST_DOESNT_EXIST),
            @ApiResponse(code = 500, message = Factory.MSG_500)
    })
    @PostMapping("/{playlistId}/musicas")
    public ResponseEntity<Void> saveMusicInPlaylist (@RequestBody MusicDto musicDto,
                                                     @PathVariable("playlistId") String playlistId,
                                                     @RequestHeader(value="name") String userName,
                                                     @RequestHeader(value="token") String userToken){
        log.info("Starting the route save music in a playlist with id:" + playlistId);
        tokenAuthorizerService.verifyTokenAuthorizer(userName, userToken);
        log.info("Authorized user:" + userName);
        var savedPlaylist = playlistService.saveMusicInPlaylist(musicDto, playlistId);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPlaylist.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Delete music in playlist", notes = "Deleted music in playlist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Factory.MSG_200_DELETE_SUCCESSFULLY, response = Object.class),
            @ApiResponse(code = 400, message = Factory.MSG_400_MUSIC_DOESNT_EXIST + "<br/>" + Factory.MSG_400_PLAYLIST_DOESNT_EXIST +  "<br/>" + Factory.MSG_400_MUSIC_DOESNT_EXIST_IN_PLAYLIST),
            @ApiResponse(code = 500, message = Factory.MSG_500)
    })
    @DeleteMapping("/{playlistId}/musicas/{musicId}")
    public ResponseEntity<String> deleteMusicFromPlaylist (@PathVariable String playlistId,
                                                           @PathVariable String musicId,
                                                           @RequestHeader(value="name") String userName,
                                                           @RequestHeader(value="token") String userToken){
        log.info("Starting the route save music in a playlist with id:" + playlistId);
        tokenAuthorizerService.verifyTokenAuthorizer(userName, userToken);
        log.info("Authorized user:" + userName);

        playlistService.deleteMusicFromPlaylist(musicId,playlistId);

        return ResponseEntity.ok(Factory.MSG_200_DELETE_SUCCESSFULLY);
    }
}
