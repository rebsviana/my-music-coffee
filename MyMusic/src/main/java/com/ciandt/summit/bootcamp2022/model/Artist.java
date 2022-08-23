package com.ciandt.summit.bootcamp2022.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Artista")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Artist {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String name;

}
