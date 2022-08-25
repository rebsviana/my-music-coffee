package com.ciandt.summit.bootcamp2022.dto;

import com.ciandt.summit.bootcamp2022.model.Artist;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class MusicDto {

    @ApiModelProperty(example = "29613f16-5019-4573-9732-8213e8bec672")
    String id;

    @ApiModelProperty(example = "All I Can Do Is Cry")
    @Length(min = 3, message = "The name must have three or more than three characters")
    @NotNull(message = "Name can't be null")
    String name;

    @ApiModelProperty(example = "9ac9d58b-ad3a-49f7-8911-74e3777cc8ab")
    Artist artistId;
}

