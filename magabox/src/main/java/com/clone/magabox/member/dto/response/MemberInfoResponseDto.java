package com.clone.magabox.member.dto.response;

import com.clone.magabox.member.entity.Member;
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
