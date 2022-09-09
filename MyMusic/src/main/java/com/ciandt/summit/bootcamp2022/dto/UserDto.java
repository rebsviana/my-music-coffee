package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserDto {

    @ApiModelProperty(example = "dd444a81-9588-4e6b-9d3d-1f1036a6eaa1")
    String id;

    @ApiModelProperty(example = "Mariana Lima")
    String name;

    @ApiModelProperty(example = "mariana")
    String nickname;

    @ApiModelProperty(example = "a39926f4-6acb-4497-884f-d4e5296ef652")
    PlaylistDto playlistId;
}

