package com.clone.magabox.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StartTimeRequestDto {
    private int hour;
    private int minute;
}
