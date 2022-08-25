package com.ciandt.summit.bootcamp2022.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Artistas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Artist {

    @ApiModelProperty(example = "9ac9d58b-ad3a-49f7-8911-74e3777cc8ab")
    @Id
    @Column(name = "Id")
    private String id;

    @ApiModelProperty(example = "Tina Turner")
    @Column(name = "Nome")
    private String name;
}
