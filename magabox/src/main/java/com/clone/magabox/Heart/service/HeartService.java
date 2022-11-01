package com.clone.magabox.Heart.service;

import com.clone.magabox.Heart.heartRepository.HeartRepository;
import com.clone.magabox.movie.repository.MovieRepository;
import com.clone.magabox.config.dto.response.ResponseDto;
import com.clone.magabox.entity.Member;
import com.clone.magabox.entity.Heart;
import com.clone.magabox.entity.Movie;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MovieRepository movieRepository;
    public ResponseDto<?> clickHeart(Long movie_id, Member member) {

        Movie movie = movieRepository.findById(movie_id).orElse(null);

        if(Objects.isNull(movie)) {
            return ResponseDto.fail(400, "Bad Request!","좋아요할 영화목록이 없어요.");
        }

        if(!heartRepository.existsByMovieAndMember(movie, member)) {
            Heart heart = new Heart(movie, member);
            heartRepository.save(heart);
            return ResponseDto.success("좋아요!");
        } else {
            Heart heart = heartRepository.findByMovieAndMember(movie, member);
            heartRepository.delete(heart);
            return ResponseDto.success("좋아요 취소");
        }
    }
}
