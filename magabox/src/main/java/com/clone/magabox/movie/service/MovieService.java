package com.clone.magabox.movie.service;

import com.clone.magabox.dto.request.MovieRequestDto;
import com.clone.magabox.dto.response.MovieResponseDto;
import com.clone.magabox.dto.response.MovieSelectOneResponseDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.entity.ERole;
import com.clone.magabox.entity.Movie;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.movie.repository.MovieRepository;
import com.clone.magabox.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<?> getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);

        if(Objects.isNull(movie)) {
            return ResponseDto.fail(400, "Bad Reqest", "찾는 영화가 없어요.");
        }

        MovieSelectOneResponseDto movieDto = new MovieSelectOneResponseDto(movie);

        return ResponseDto.success(movieDto);
    }

    public ResponseDto<?> getAllMovies(){

        List<Movie> products = movieRepository.findAll();

        List<MovieResponseDto> productResponseDtoList = new ArrayList<>();

        for (Movie product : products) {
            productResponseDtoList.add(new MovieResponseDto(product));
        }

        return ResponseDto.success(productResponseDtoList);
    }

    public ResponseDto<?> createMovie(MovieRequestDto movieRequestDto, MemberDetailsImpl memberDetails) throws IOException {

        if (Objects.isNull(memberDetails.getMember())) {
            return ResponseDto.fail(401, "Unauthorized", "현재 로그인이 안되있습니다");
        } else if (memberDetails.getMember().getErole() != ERole.ROLE_ADMIN) {
            return ResponseDto.fail(403, "Forbidden Request", "관리자만 영화 등록할수 있습니다");
        }

        if(movieRequestDto.getFile().isEmpty()) {
            Movie movie = Movie.builder()
                    .title(movieRequestDto.getTitle())
                    .desc(movieRequestDto.getDesc())
                    .imageUrl(null)
                    .member(memberDetails.getMember())
                    .runtime(movieRequestDto.getRuntime())
                    .build();
            movieRepository.save(movie);
        } else {
            Movie movie = Movie.builder()
                    .title(movieRequestDto.getTitle())
                    .desc(movieRequestDto.getDesc())
                    .imageUrl(s3Uploader.upload(movieRequestDto.getFile(), "movies"))
                    .member(memberDetails.getMember())
                    .runtime(movieRequestDto.getRuntime())
                    .build();
            movieRepository.save(movie);
        }

        return ResponseDto.success("성공적으로 등록하셨습니다");
    }

    @Transactional
    public ResponseDto<?> update(Long id, MovieRequestDto movieRequestDto, MemberDetailsImpl memberDetails) throws IOException {

        Movie findMovie = movieRepository.findById(id).orElse(null);

        if(Objects.isNull(findMovie)) {
            ResponseDto.fail(400, "Bad Reqest", "찾는 영화가 없어요.");
        }

        if(movieRequestDto.getFile().isEmpty()) {
            Movie movie = Movie.builder()
                    .id(findMovie.getId())
                    .title(movieRequestDto.getTitle())
                    .desc(movieRequestDto.getDesc())
                    .imageUrl(null)
                    .member(memberDetails.getMember())
                    .runtime(movieRequestDto.getRuntime())
                    .build();
            movieRepository.save(movie);
        } else {
            Movie movie = Movie.builder()
                    .id(findMovie.getId())
                    .title(movieRequestDto.getTitle())
                    .desc(movieRequestDto.getDesc())
                    .imageUrl(s3Uploader.upload(movieRequestDto.getFile(), "movies"))
                    .member(memberDetails.getMember())
                    .runtime(movieRequestDto.getRuntime())
                    .build();
            movieRepository.save(movie);
        }
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
}
