package com.ciandt.summit.bootcamp2022.model;

import com.ciandt.summit.bootcamp2022.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String name;

    @Column(name = "Nickname")
    private String nickname;

    @Column(name = "UserType")
    private UserType userType;

    @OneToOne
    @JoinColumn(name = "Playlistid", referencedColumnName = "Id")
    private Playlist playlistId;
}
