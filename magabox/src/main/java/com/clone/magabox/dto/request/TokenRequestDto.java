package com.clone.magabox.dto.request;

import lombok.NoArgsConstructor;
import lombok.Getter;


@Getter
@NoArgsConstructor
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
