package com.clone.magabox.starttime.service;

import com.clone.magabox.dto.request.StartTimeRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.entity.Movie;
import com.clone.magabox.entity.StartTime;
import com.clone.magabox.repository.MovieRepository;
import com.clone.magabox.repository.StartTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StartTimeService {

    private final MovieRepository movieRepository;
    private final StartTimeRepository startTimeRepository;

    @Transactional
    public ResponseDto<?> addStartTime(Long movieId, StartTimeRequestDto startTimeRequestDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new NullPointerException("해당 영화가 없습니다")
        );
        StartTime startTime = new StartTime().builder()
                .movie(movie)
                .localTime(LocalTime.of(startTimeRequestDto.getHour(), startTimeRequestDto.getMinute()))
                .build();
        startTimeRepository.save(startTime);

        return ResponseDto.success("상영시간 추가 성공");
    }

    @Transactional
    public ResponseDto<?> deleteStartTime(Long timeId) {
        StartTime startTime = startTimeRepository.findById(timeId).orElseThrow(
                () -> new NullPointerException("해당 상영시간이 없습니다")
        );
        startTimeRepository.delete(startTime);

        return ResponseDto.success("상영시간 삭제 성공");
    }
}
