package com.clone.magabox.config.dto.response;

import com.clone.magabox.entity.Member;
import lombok.Getter;

@Getter
public class MemberInfoResponseDto {
    private String username;
    private String name;

    public MemberInfoResponseDto(Member user){
        this.username = user.getUsername();
        this.name = user.getName();
    }
}
