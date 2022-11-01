package com.clone.magabox.Heart.dto.response;

import com.clone.magabox.Heart.entity.Heart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeartResonseDto {
    private String username;
    private Long memberId;

    public HeartResonseDto(Heart heart) {
        this.username = heart.getMember().getUsername();
        this.memberId = heart.getMember().getId();
    }
}
