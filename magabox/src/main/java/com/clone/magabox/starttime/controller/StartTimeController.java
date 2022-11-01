package com.clone.magabox.starttime.controller;


import com.clone.magabox.dto.request.StartTimeRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.member.service.MemberDetailsImpl;
import com.clone.magabox.starttime.service.StartTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class StartTimeController {
    private final StartTimeService startTimeService;

    @PostMapping("/{movieId}")
    public ResponseDto<?> addStartTime(@PathVariable Long movieId, @RequestBody StartTimeRequestDto startTimeRequestDto,
                                       @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return startTimeService.addStartTime(movieId, startTimeRequestDto, memberDetails);
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseDto<?> deleteStartTime(@PathVariable Long timeId,
                                          @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return startTimeService.deleteStartTime(timeId, memberDetails);
    }

    @GetMapping("/times/{movieId}")
    public ResponseDto<?> getMovieStartTime(@PathVariable Long movieId) {
        return startTimeService.getMovieStartTime(movieId);
    }
}
