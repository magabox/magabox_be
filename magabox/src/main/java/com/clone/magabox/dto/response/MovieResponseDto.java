package com.clone.magabox.dto.response;

import com.clone.magabox.entity.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MovieResponseDto {

    private Long id;
    private String title;
    private String desc;
    private String imageUrl;

    public MovieResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.desc = movie.getDesc();
        this.imageUrl = movie.getImageUrl();
    }
}
