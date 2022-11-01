package com.clone.magabox.movie.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.movie.dto.request.MovieRequestDto;
import com.clone.magabox.movie.service.MovieService;
import com.clone.magabox.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    /**
     * 검색
     */
    @GetMapping("/search")
    public ResponseDto<?> searchMovies(@RequestParam("word") String word) {
        return movieService.searchMovies(word);
    }

    /**
     * 전체에서 4개출력
     */
    @GetMapping("/index")
    public ResponseDto<?> getMoviesRank() {
        return movieService.getMoviesRank();
    }

    /**
     * 전체조회
     */
    @GetMapping
    public ResponseDto<?> getMovies() {
        return movieService.getAllMovies();
    }

    /**
     * 단일조회
     */
    @GetMapping("/{movieId}")
    public ResponseDto<?> theMovies(@PathVariable Long movieId){
        return movieService.getMovie(movieId);
    }

    /**
     * 영화게시
     */
    @PostMapping
    public ResponseDto<?> Posting(
            @ModelAttribute MovieRequestDto movieRequestDto,
            @AuthenticationPrincipal MemberDetailsImpl memberDetails
    ) throws IOException {
        return movieService.createMovie(movieRequestDto, memberDetails);
    }

    /**
     * 영화삭제
     */
    @DeleteMapping("/{movieId}")
    public ResponseDto<?> deleteProduct(@PathVariable Long movieId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return movieService.deleteMovie(movieId, memberDetails);
    }

    /**
     * 영화수정
     */
    @PutMapping( "/{movieId}")
    public ResponseDto<?> updateMovies(
            @PathVariable Long movieId,
            @ModelAttribute MovieRequestDto movieRequestDto,
            @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return movieService.update(movieId, movieRequestDto, memberDetails);
    }
}
