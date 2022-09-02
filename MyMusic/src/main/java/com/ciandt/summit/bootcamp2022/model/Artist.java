package com.ciandt.summit.bootcamp2022.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Artistas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Artist {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String name;
}
