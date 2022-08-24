package com.ciandt.summit.bootcamp2022.dto;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

public class ArtistDto {

    String id;

    @Length(min = 3, message = "The name must have three or more then three characters")
    @NotNull(message = "Name can't be null")
    String name;
}
