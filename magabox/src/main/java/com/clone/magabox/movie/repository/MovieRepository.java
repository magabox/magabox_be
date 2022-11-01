package com.clone.magabox.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.clone.magabox.movie.entity.Movie;

import java.util.Optional;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Boolean existsByTitle(String title);
    Optional<Movie> findByTitle(String title);
    @Query("select m from Movie m where m.title LIKE %:word%")
    List<Movie> findByWord(@Param("word") String word);
    List<Movie> findTop4ByOrderByTotalHeartCountDesc();
}
