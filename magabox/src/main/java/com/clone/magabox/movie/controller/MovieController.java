package com.clone.magabox.movie.controller;


import com.clone.magabox.dto.request.MovieRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    public ResponseDto<?> getMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/movies/{movieId}")
    public ResponseDto<?> theMovies(@PathVariable Long movieId){
        return movieService.getMovie(movieId);
    }

    @PostMapping("/movies")
    public ResponseDto<?> Posting(
            @ModelAttribute MovieRequestDto movieRequestDto,
            @AuthenticationPrincipal MemberDetailsImpl memberDetails
    ) throws IOException {
        return movieService.createMovie(movieRequestDto, memberDetails);
    }

    @DeleteMapping("/movies/{movieId}")
    public ResponseDto<?> deleteProduct(@PathVariable Long movieId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return movieService.deleteMovie(movieId, memberDetails);
    }

    @PutMapping(value = "/movies/{movieId}")
    public ResponseDto<?> updateMovies(
            @PathVariable Long movieId,
            @ModelAttribute MovieRequestDto movieRequestDto,
            @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return movieService.update(movieId, movieRequestDto, memberDetails);
    }
}
