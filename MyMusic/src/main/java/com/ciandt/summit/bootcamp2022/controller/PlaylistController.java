package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.impl.PlaylistServiceImpl;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/playlists", produces = "application/json")
@Log4j2
@Tag(name = "Playlists")
public class PlaylistController {

    @Autowired
    private PlaylistServiceImpl playlistService;
    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;
    @Autowired
    private HttpServletRequest request;

    @Operation(summary = "Save music in playlist" , security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "201", description = Factory.MSG_201_CREATED, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = Factory.MSG_400_MUSIC_DOESNT_EXIST + "<br/>" +
                    Factory.MSG_400_PLAYLIST_DOESNT_EXIST + "<br/>" +
                    Factory.MESSAGE_BAD_REQUEST_PAYLOAD + "<br/>" +
                    Factory.MSG_400_USER_DOESNT_EXIST + "<br/>" +
                    Factory.MSG_400_PLAYLIST_DOESNT_EXIST_ON_USER + "<br/>" +
                    Factory.MSG_400_MAX_MUSIC_CAPACITY_USER_COMMON + "<br/>", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = UnauthorizedAccessException.MESSAGE, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = Factory.MSG_500, content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping("/{playlistId}/{nickname}/music")
    public ResponseEntity<Void> saveMusicInPlaylist (@RequestBody MusicDto musicDto,
                                                     @PathVariable String playlistId,
                                                     @PathVariable String nickname){
        log.info("Starting the route save music in a playlist with id:" + playlistId);
        tokenAuthorizerService.verifyTokenAuthorizer(request.getHeader("Authorization"));
        log.info("User authenticated successfully");

        var savedPlaylist = playlistService.saveMusicInPlaylist(musicDto, playlistId, nickname);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPlaylist.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Delete music from playlist", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = Factory.MSG_200_MUSIC_DELETE_SUCCESSFULLY, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = Factory.MSG_400_MUSIC_DOESNT_EXIST, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = UnauthorizedAccessException.MESSAGE, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = Factory.MSG_500, content = @Content(schema = @Schema(hidden = true))),
    })
    @DeleteMapping("/{playlistId}/musicas/{musicId}")
    public ResponseEntity<String> deleteMusicFromPlaylist (@PathVariable String playlistId,
                                                           @PathVariable String musicId){
        log.info("Starting the route save music in a playlist with id:" + playlistId);
        tokenAuthorizerService.verifyTokenAuthorizer(request.getHeader("Authorization"));
        log.info("User authenticated successfully");

        playlistService.deleteMusicFromPlaylist(musicId,playlistId);

        return ResponseEntity.ok(Factory.MSG_200_MUSIC_DELETE_SUCCESSFULLY);
    }
}
