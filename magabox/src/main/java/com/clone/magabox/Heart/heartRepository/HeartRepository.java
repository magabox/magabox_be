package com.clone.magabox.Heart.heartRepository;

import com.clone.magabox.entity.Heart;
import com.clone.magabox.entity.Member;
import com.clone.magabox.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsByMovieAndMember(Movie movie, Member member);
    Heart findByMovieAndMember(Movie movie,Member member);
}
