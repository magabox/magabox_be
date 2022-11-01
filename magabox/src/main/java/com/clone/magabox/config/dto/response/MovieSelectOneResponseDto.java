package com.clone.magabox.config.dto.response;

import com.clone.magabox.entity.Movie;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;
import java.util.List;


@Getter
@NoArgsConstructor
public class MovieSelectOneResponseDto {

    private Long id;
    private String title;
    private String summary;
    private String imageUrl;
    private int runtime;

    private List<CommentResponseDto> commentList;

    public MovieSelectOneResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.summary = movie.getSummary();
        this.imageUrl = movie.getImageUrl();
        this.runtime = movie.getRuntime();
        this.commentList = movie.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
