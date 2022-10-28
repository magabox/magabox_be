package com.clone.magabox.repository;

import com.clone.magabox.entity.Comment;
import com.clone.magabox.entity.Member;
import com.clone.magabox.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Boolean existsByMovieAndMember(Movie movie, Member member);
    Optional<Comment> findByMovieAndMember(Movie movie, Member member);
    List<Comment> findByMovie(Movie movie);
}
