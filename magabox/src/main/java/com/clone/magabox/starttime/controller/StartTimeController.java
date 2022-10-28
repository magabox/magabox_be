package com.clone.magabox.starttime.controller;


import com.clone.magabox.dto.request.StartTimeRequestDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.starttime.service.StartTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class StartTimeController {
    private final StartTimeService startTimeService;

    @PostMapping("/{movieId}")
    public ResponseDto<?> addStartTime(@PathVariable Long movieId, @RequestBody StartTimeRequestDto startTimeRequestDto) {
        return startTimeService.addStartTime(movieId, startTimeRequestDto);
    }

    @DeleteMapping("/times/{timeId}")
    public ResponseDto<?> deleteStartTime(@PathVariable Long timeId) {
        return startTimeService.deleteStartTime(timeId);
    }
}
