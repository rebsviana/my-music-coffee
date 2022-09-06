package com.ciandt.summit.bootcamp2022.repository;

import com.ciandt.summit.bootcamp2022.model.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, String> {

    @Query(
            value = "SELECT DISTINCT * FROM Musicas m INNER JOIN Artistas a ON a.Id =  m.ArtistaId WHERE UPPER(a.nome) " +
                    "LIKE UPPER ('%' || :name || '%') OR UPPER(m.nome) LIKE UPPER ('%' || :name || '%') ORDER BY m.nome, a.nome"
            , nativeQuery = true
    )
    List<Music> findMusicByNameOrArtist(@Param("name") String name);
}
