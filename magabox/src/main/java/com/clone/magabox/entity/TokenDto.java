package com.clone.magabox.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpireIn;
}