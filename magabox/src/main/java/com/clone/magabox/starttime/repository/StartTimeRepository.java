package com.clone.magabox.starttime.repository;

import com.clone.magabox.movie.entity.Movie;
import com.clone.magabox.reservation.entity.StartTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StartTimeRepository extends JpaRepository<StartTime, Long> {
    Boolean existsByMovie(Movie movie);
    List<StartTime> findByMovie(Movie movie);
}
