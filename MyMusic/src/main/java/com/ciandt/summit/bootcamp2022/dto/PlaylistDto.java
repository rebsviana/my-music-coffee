package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlaylistDto {

    @ApiModelProperty(example = "a39926f4-6acb-4497-884f-d4e5296ef652")
    String id;
}
