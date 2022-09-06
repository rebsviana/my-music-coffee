package com.ciandt.summit.bootcamp2022.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Playlist {

    @Id
    @Column(name = "Id")
    private String id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Playlistmusicas",
            joinColumns =  @JoinColumn(name = "Playlistid"),
            inverseJoinColumns =  @JoinColumn(name = "Musicaid")
    )
    @JsonIgnore
    Set<Music> musics = new HashSet<>();
}
