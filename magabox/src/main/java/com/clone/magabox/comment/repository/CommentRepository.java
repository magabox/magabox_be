package com.clone.magabox.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clone.magabox.comment.entity.Comment;
import com.clone.magabox.movie.entity.Movie;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Transactional
    @Query("delete from Comment where movie in :movie")
    void deleteByMovie(@Param("movie") Movie movie);
}
