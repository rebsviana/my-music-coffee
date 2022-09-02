package com.ciandt.summit.bootcamp2022.repository;

import com.ciandt.summit.bootcamp2022.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistsRepository extends JpaRepository<Playlist, String> {
}
