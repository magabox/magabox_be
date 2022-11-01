package com.clone.magabox.member.controller;

import com.clone.magabox.config.dto.request.MemberRequestDto;
import com.clone.magabox.config.dto.request.TokenRequestDto;
import com.clone.magabox.member.service.MemberService;
import com.clone.magabox.config.dto.request.ValidatorIdDto;
import com.clone.magabox.config.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.clone.magabox.entity.TokenDto;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    // TODO: update fail status

    private final MemberService memberService;

    /*
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.signup(memberRequestDto);
    }

    /*
     * 로그인
     */
    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse response) {
        return memberService.login(memberRequestDto, response);
    }

    /*
     * 중복확인
     */
    @PostMapping("/checkDuplicate")
    public ResponseDto<?> checkDuplicate(@RequestBody ValidatorIdDto validatorIdDto){
        return memberService.checkDuplicate(validatorIdDto);
    }

    @PostMapping("/reissue")
    public ResponseDto<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = new TokenDto();
        return ResponseDto.success(tokenDto);
    }
}
