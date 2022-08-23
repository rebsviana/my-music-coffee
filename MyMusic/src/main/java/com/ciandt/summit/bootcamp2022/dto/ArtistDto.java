package com.ciandt.summit.bootcamp2022.dto;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

public class ArtistDto {

    String id;

    @Length(min = 3, message = "The name have to be three characters")
    @NotNull
    String name;
}
