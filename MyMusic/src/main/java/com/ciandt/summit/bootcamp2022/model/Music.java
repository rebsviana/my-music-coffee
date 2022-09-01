package com.ciandt.summit.bootcamp2022.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Musicas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Music {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String name;

    @OneToOne
    @JoinColumn(name = "Artistaid", referencedColumnName = "Id")
    private Artist artistId;

    @ManyToMany(mappedBy = "musics")
    @JsonIgnore
    private Set<Playlist> playlists = new HashSet<>();

    public Music(String id, String name, Artist artistId) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
    }
}
