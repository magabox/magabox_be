package com.clone.magabox.starttime.service;

import com.clone.magabox.dto.request.StartTimeRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.dto.response.StartTimeResponseDto;
import com.clone.magabox.entity.ERole;
import com.clone.magabox.entity.Movie;
import com.clone.magabox.entity.StartTime;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.repository.MovieRepository;
import com.clone.magabox.repository.StartTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StartTimeService {

    private final MovieRepository movieRepository;
    private final StartTimeRepository startTimeRepository;

    @Transactional
    public ResponseDto<?> addStartTime(Long movieId, StartTimeRequestDto startTimeRequestDto,
                                       MemberDetailsImpl memberDetails) throws IllegalAccessException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new NullPointerException("해당 영화가 없습니다")
        );

        if (memberDetails.getMember().getErole() != ERole.ROLE_ADMIN) {
            throw new IllegalAccessException("현재 관리자가 아닙니다");
        }

        StartTime startTime = new StartTime().builder()
                .movie(movie)
                .startingTime(LocalTime.of(startTimeRequestDto.getHour(), startTimeRequestDto.getMinute()))
                .capacity(200) // movie theater capacity
                .build();
        startTimeRepository.save(startTime);

        return ResponseDto.success("상영시간 추가 성공");
    }

    @Transactional
    public ResponseDto<?> deleteStartTime(Long timeId, MemberDetailsImpl memberDetails) throws IllegalAccessException {
        if (memberDetails.getMember().getErole() != ERole.ROLE_ADMIN) {
            throw new IllegalAccessException("현재 관리자가 아닙니다");
        }
        StartTime startTime = startTimeRepository.findById(timeId).orElseThrow(
                () -> new NullPointerException("해당 상영시간이 없습니다")
        );
        startTimeRepository.delete(startTime);

        return ResponseDto.success("상영시간 삭제 성공");
    }

    @Transactional
    public ResponseDto<?> getMovieStartTime(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new NullPointerException("해당 영화가 없습니다")
        );

        List<StartTime> startTimeList = startTimeRepository.findByMovie(movie);

        List<StartTimeResponseDto> startTimeListDto = new ArrayList<>();

        for (StartTime startTime : startTimeList) {
            startTimeListDto.add(new StartTimeResponseDto(startTime));
        }
        return ResponseDto.success(startTimeListDto);
    }
}
