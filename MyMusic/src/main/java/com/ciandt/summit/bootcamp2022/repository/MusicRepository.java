package com.ciandt.summit.bootcamp2022.repository;

import com.ciandt.summit.bootcamp2022.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, String> {

    @Query(
            value = "SELECT * FROM Artistas a INNER JOIN Musicas m ON a.Id = m.ArtistaId WHERE a.Nome LIKE " +
            " '%:name%' OR m.nome LIKE '%:name%' ORDER BY a.nome ASC, m.nome ASC;",
             nativeQuery = true
    )
    List<Music> searchMusicNameOrArtistName(@Param("name") String name);
}
