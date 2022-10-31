package com.clone.magabox.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clone.magabox.entity.Comment;
import com.clone.magabox.entity.Member;
import com.clone.magabox.entity.Movie;

import java.util.Optional;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Boolean existsByMovieAndMember(Movie movie, Member member);
    Optional<Comment> findByMovieAndMember(Movie movie, Member member);
    List<Comment> findByMovie(Movie movie);
}
