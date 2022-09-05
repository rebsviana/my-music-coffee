package com.ciandt.summit.bootcamp2022.dto;

import com.ciandt.summit.bootcamp2022.model.Music;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PlaylistDto {

    @ApiModelProperty(example = "a39926f4-6acb-4497-884f-d4e5296ef652")
    String id;

    @JsonIgnore
    Set<Music> musics;

    public PlaylistDto(String id) {
        this.id = id;
    }
}
