package com.ciandt.summit.bootcamp2022.dto;

import com.ciandt.summit.bootcamp2022.model.Artist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class MusicDto {

    String id;

    @Length(min = 3, message = "The name must have three or more than three characters")
    @NotNull(message = "Name can't be null")
    String name;

    Artist artistId;
}

