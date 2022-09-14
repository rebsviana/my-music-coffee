package com.ciandt.summit.bootcamp2022.dto;

import com.ciandt.summit.bootcamp2022.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    @Schema(example = "dd444a81-9588-4e6b-9d3d-1f1036a6eaa1")
    String id;

    @Schema(example = "Mariana Lima")
    String name;

    @Schema(example = "mariana")
    String nickname;

    @Schema(example = "PREMIUM")
    UserType userType;

    @Schema(implementation = PlaylistDto.class)
    PlaylistDto playlistId;
}

