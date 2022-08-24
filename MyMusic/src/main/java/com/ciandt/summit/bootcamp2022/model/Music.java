package com.ciandt.summit.bootcamp2022.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Musicas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Music {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String nome;

    @OneToOne
    @JoinColumn(name = "ArtistaId", referencedColumnName = "Id")
    private Artist artistId;

}
