package com.clone.magabox.starttime.dto.response;

import com.clone.magabox.reservation.entity.StartTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class StartTimeResponseDto {
    private Long timeId;
    private LocalTime startingTime;

    public StartTimeResponseDto(StartTime startTime) {
        this.timeId = startTime.getId();
        this.startingTime = startTime.getStartingTime();
    }
}
