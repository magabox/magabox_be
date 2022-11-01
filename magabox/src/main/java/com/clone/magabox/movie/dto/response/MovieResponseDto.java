package com.clone.magabox.movie.dto.response;

import com.clone.magabox.Heart.dto.response.HeartResonseDto;
import com.clone.magabox.movie.entity.Movie;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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
    private List<HeartResonseDto> heartList;

    public MovieResponseDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.summary = movie.getSummary();
        this.imageUrl = movie.getImageUrl();
        this.runtime = movie.getRuntime();
        this.totalHeartCount = movie.getTotalHeartCount();
        this.totalRating = movie.getTotalRating();
        this.heartList = movie.getHeartList().stream().map(HeartResonseDto::new).collect(Collectors.toList());
    }
}
