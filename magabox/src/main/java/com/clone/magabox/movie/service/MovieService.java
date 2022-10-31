package com.clone.magabox.movie.service;

import com.clone.magabox.dto.response.MovieSelectOneResponseDto;
import org.springframework.transaction.annotation.Transactional;
import com.clone.magabox.movie.repository.MovieRepository;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.dto.response.MovieResponseDto;
import com.clone.magabox.dto.request.MovieRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import org.springframework.stereotype.Service;
import com.clone.magabox.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import com.clone.magabox.entity.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<?> getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if(Objects.isNull(movie)) {
            return ResponseDto.fail(400, "Bad Request", "찾는 영화가 없어요.");
        }

        MovieSelectOneResponseDto movieDto = new MovieSelectOneResponseDto(movie);

        return ResponseDto.success(movieDto);
    }

    public ResponseDto<?> getMoviesRank() {
        List<Movie> movies = movieRepository.findTop4ByOrderByTotalHeartCountDesc();

        List<MovieResponseDto> moviesList = new ArrayList<>();
        for (Movie movie : movies) {
            moviesList.add(new MovieResponseDto(movie));
        }

        return ResponseDto.success(moviesList);
    }

    public ResponseDto<?> getAllMovies(){

        List<Movie> Movies = movieRepository.findAll();

        List<MovieResponseDto> movieResponseDtoList = new ArrayList<>();

        for (Movie movie : Movies) {
            movieResponseDtoList.add(new MovieResponseDto(movie));
        }

        return ResponseDto.success(movieResponseDtoList);
    }

    @Transactional
        public ResponseDto<?> createMovie(MovieRequestDto movieRequestDto, MemberDetailsImpl memberDetails) throws IOException {
        Movie findTitle = movieRepository.findByTitle(movieRequestDto.getTitle()).orElse(null);
        if(Objects.nonNull(findTitle)){
            return ResponseDto.fail(400,"You cannot write with the same title.","동일한 제목으로 작성 불가능");
        }

        Movie movie = Movie.builder()
                .title(movieRequestDto.getTitle())
                .summary(movieRequestDto.getSummary())
                .imageUrl(movieRequestDto.getFile().isEmpty() ? null : s3Uploader.upload(movieRequestDto.getFile(), "movies"))
                .member(memberDetails.getMember())
                .runtime(movieRequestDto.getRuntime())
                .build();
        movieRepository.save(movie);

        return ResponseDto.success("성공적으로 등록하셨습니다");
    }

    @Transactional
    public ResponseDto<?> update(Long id, MovieRequestDto movieRequestDto, MemberDetailsImpl memberDetails) throws IOException {

        Movie findMovie = movieRepository.findById(id).orElse(null);

        if(Objects.isNull(findMovie)) {
            ResponseDto.fail(400, "Bad Request", "찾는 영화가 없어요.");
        }

        Movie findTitle = movieRepository.findByTitle(movieRequestDto.getTitle()).orElse(null);
        if(Objects.nonNull(findTitle)){
            return ResponseDto.fail(400,"It cannot be edited with the same title.","동일한 제목으로 수정 불가능");
        }

        Movie movie = Movie.builder()
                .id(id)
                .title(movieRequestDto.getTitle())
                .summary(movieRequestDto.getSummary())
                .imageUrl(movieRequestDto.getFile().isEmpty()? null:s3Uploader.upload(movieRequestDto.getFile(), "movies"))
                .member(memberDetails.getMember())
                .runtime(movieRequestDto.getRuntime())
                .build();

        movieRepository.save(movie);

        return ResponseDto.success("업데이트 성공");
    }

    @Transactional
    public ResponseDto<?> deleteMovie(Long movieId, MemberDetailsImpl memberDetails) {

        Movie findMovie = movieRepository.findById(movieId).orElse(null);

        if(Objects.isNull(findMovie)) {
            ResponseDto.fail(400, "Bad Reqest", "찾는 영화가 없어요.");
        }

        if(memberDetails.getMember().getUsername().equals(findMovie.getMember().getUsername())) {

            movieRepository.delete(findMovie);

            return ResponseDto.success("성공적으로 삭제하였습니다.");

        } else {
            return ResponseDto.fail(401,"You do not have permission","권한이 없어요.");
        }
    }

    public ResponseDto<?> searchMovies(String word) {

        List<Movie> movies = movieRepository.findByWord(word);

        List<MovieResponseDto> movieResponseDtoList = new ArrayList<>();

        for (Movie movie : movies) {
            movieResponseDtoList.add(new MovieResponseDto(movie));
        }

        return ResponseDto.success(movieResponseDtoList);
    }
}
