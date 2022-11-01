package com.clone.magabox.dto.response;

import com.clone.magabox.entity.StartTime;

import java.time.LocalTime;

public class StartTimeResponseDto {
    private Long timeId;
    private LocalTime startingTime;

    public StartTimeResponseDto(StartTime startTime) {
        this.timeId = startTime.getId();
        this.startingTime = startTime.getStartingTime();
    }
}
