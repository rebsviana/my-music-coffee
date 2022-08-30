package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Getter
public class PlaylistDto {

    @NotNull
    @ApiModelProperty(example = "a39926f4-6acb-4497-884f-d4e5296ef652")
    String id;
}
