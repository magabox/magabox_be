package com.clone.magabox.repository;

import com.clone.magabox.entity.Movie;
import com.clone.magabox.entity.StartTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StartTimeRepository extends JpaRepository<StartTime, Long> {
    Boolean existsByMovie(Movie movie);
    List<StartTime> findByMovie(Movie movie);
}
