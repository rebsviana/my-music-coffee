package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.exceptions.UnauthorizedAccessException;
import com.ciandt.summit.bootcamp2022.service.impl.MusicServiceImpl;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/music", produces = "application/json")
@SecurityRequirement(name = "bearerAuth")
@Log4j2
@Tag(name = "Music")
public class MusicController {

    @Autowired
    private MusicServiceImpl musicService;
    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;
    @Autowired
    private HttpServletRequest request;

    @Operation(summary = "Get some music with filter", security = @SecurityRequirement(name = "bearerAuth"), responses = {
            @ApiResponse(responseCode = "200", description = Factory.MSG_200_OK, content = @Content(mediaType = "application/json", schema = @Schema(implementation = MusicDto.class))),
            @ApiResponse(responseCode = "204", description = NoContentException.MESSAGE, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = MinLengthRequiredException.MESSAGE, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = UnauthorizedAccessException.MESSAGE, content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = Factory.MSG_500, content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping
    public ResponseEntity<PageDecoratorDto<MusicDto>> getMusicByNameOrArtistWithFilter(@RequestParam("filtro") String filterName) {
        log.info("Starting the route search new music with filter " + filterName);
        //tokenAuthorizerService.verifyTokenAuthorizer(request.getHeader("Authorization"));
        log.info("User authenticated successfully");

        var pageMusicDto = musicService.getMusicByNameOrArtist(filterName);

        return ResponseEntity.ok(pageMusicDto);
    }
}
