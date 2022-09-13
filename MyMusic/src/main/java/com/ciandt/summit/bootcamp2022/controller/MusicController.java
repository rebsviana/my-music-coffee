package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PageDecoratorDto;
import com.ciandt.summit.bootcamp2022.exceptions.MinLengthRequiredException;
import com.ciandt.summit.bootcamp2022.exceptions.NoContentException;
import com.ciandt.summit.bootcamp2022.service.impl.MusicServiceImpl;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;


@RestController
@RequestMapping(value = "/api/v1/music", produces = "application/json")
@Log4j2
public class MusicController {

    @Autowired
    private MusicServiceImpl musicService;

    @Autowired
    private TokenAuthorizerService tokenAuthorizerService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "Get some music with filter", notes = "Returns a list of music")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Factory.MSG_200_OK),
            @ApiResponse(code = 204, message = NoContentException.MESSAGE, response = Object.class),
            @ApiResponse(code = 400, message = MinLengthRequiredException.MESSAGE),
            @ApiResponse(code = 500, message = Factory.MSG_500)
    })
    @GetMapping
    public ResponseEntity<PageDecoratorDto<MusicDto>> getMusicByNameOrArtistWithFilter(@RequestParam("filtro") String filterName) {
        log.info("Starting the route search new music with filter " + filterName);

        var authorizationEncode = request.getHeader("Authorization").substring(7);
        var anything = Base64.getDecoder().decode(authorizationEncode);
        var authorization = new String(anything);
        var user = authorization.substring(0, authorization.indexOf(":"));
        var password = authorization.substring(authorization.indexOf(":") + 1);

        tokenAuthorizerService.verifyTokenAuthorizer(user, password);
        log.info("Authorized user:" + user);
        var pageMusicDto = musicService.getMusicByNameOrArtist(filterName);

        return ResponseEntity.ok(pageMusicDto);
    }
}
