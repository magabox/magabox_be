package com.clone.magabox.Heart.controller;

import com.clone.magabox.Heart.service.HeartService;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.member.service.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/hearts/{movie_id}")
    public ResponseDto<?> clickHeart(@PathVariable Long movie_id, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return heartService.clickHeart(movie_id, memberDetails.getMember());
    }

}
