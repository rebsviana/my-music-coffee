package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MusicDto {

    @Schema(example = "29613f16-5019-4573-9732-8213e8bec672")
    String id;

    @Schema(example = "All I Can Do Is Cry")
    String name;

    @Schema(example = "9ac9d58b-ad3a-49f7-8911-74e3777cc8ab")
    ArtistDto artistId;
}

