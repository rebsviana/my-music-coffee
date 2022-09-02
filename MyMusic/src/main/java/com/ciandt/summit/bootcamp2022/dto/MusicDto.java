package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Getter
public class MusicDto {

    @ApiModelProperty(example = "29613f16-5019-4573-9732-8213e8bec672")
    @NotNull (message = "Payload body incorrect: id of music cannot be null")
    String id;

    @ApiModelProperty(example = "All I Can Do Is Cry")
    @NotNull (message = "Payload body incorrect: name of music cannot be null")
    String name;

    @ApiModelProperty(example = "9ac9d58b-ad3a-49f7-8911-74e3777cc8ab")
    @NotNull (message = "Payload body incorrect: artist of music cannot be null")
    @Valid ArtistDto artistId;
}

