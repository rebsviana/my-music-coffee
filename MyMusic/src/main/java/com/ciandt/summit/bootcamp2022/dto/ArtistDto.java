package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class ArtistDto {

    @ApiModelProperty(example = "9ac9d58b-ad3a-49f7-8911-74e3777cc8ab")
    @NotNull(message = "Payload body incorrect: id of artist cannot be null")
    String id;

    @ApiModelProperty(example = "Tina Turner")
    @NotNull(message = "Payload body incorrect: name of artist cannot be null")
    String name;
}
