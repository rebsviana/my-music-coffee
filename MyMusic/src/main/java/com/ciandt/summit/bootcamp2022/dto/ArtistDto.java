package com.ciandt.summit.bootcamp2022.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class ArtistDto {

    @ApiModelProperty(example = "9ac9d58b-ad3a-49f7-8911-74e3777cc8ab")
    String id;

    @ApiModelProperty(example = "Tina Turner")
    @Length(min = 3, message = "The name must have three or more then three characters")
    @NotNull(message = "Name can't be null")
    String name;
}
