package com.clone.magabox.repository;

import com.clone.magabox.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Boolean existsByTitle(String title);
    Optional<Movie> findByTitle(String title);
}
