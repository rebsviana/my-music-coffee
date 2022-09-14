package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ArtistDto {

    @Schema(example = "9ac9d58b-ad3a-49f7-8911-74e3777cc8ab")
    String id;

    @Schema(example = "Tina Turner")
    String name;
}
