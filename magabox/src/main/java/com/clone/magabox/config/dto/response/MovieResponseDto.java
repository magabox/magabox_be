package com.clone.magabox.config.dto.response;

import com.clone.magabox.entity.Movie;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
public class MovieResponseDto {

    private Long id;
    private String title;
    private String summary;
    private String imageUrl;
    private int runtime;
    private int totalHeartCount;
    private Float totalRating;

    public MovieResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.summary = movie.getSummary();
        this.imageUrl = movie.getImageUrl();
        this.runtime = movie.getRuntime();
        this.totalHeartCount = movie.getTotalHeartCount();
        this.totalRating = movie.getTotalRating();
    }
}
