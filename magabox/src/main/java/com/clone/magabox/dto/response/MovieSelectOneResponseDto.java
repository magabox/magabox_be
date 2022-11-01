package com.clone.magabox.dto.response;

import com.clone.magabox.entity.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MovieSelectOneResponseDto {

    private Long id;
    private String title;
    private String desc;
    private String imageUrl;
    private int runtime;

    private List<CommentResponseDto> commentList;

    public MovieSelectOneResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.desc = movie.getDesc();
        this.imageUrl = movie.getImageUrl();
        this.runtime = movie.getRuntime();
        this.commentList = movie.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
