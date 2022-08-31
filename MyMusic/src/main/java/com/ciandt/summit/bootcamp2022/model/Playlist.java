package com.ciandt.summit.bootcamp2022.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Playlists")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Playlist {

    @Id
    @Column(name = "Id")
    private String id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Playlistmusicas",
            joinColumns =  @JoinColumn(name = "Playlistid"),
            inverseJoinColumns =  @JoinColumn(name = "Musicaid")
    )
    @JsonIgnore
    Set<Music> musics = new HashSet<>();

    public Playlist(String id) {
        this.id = id;
    }
}
