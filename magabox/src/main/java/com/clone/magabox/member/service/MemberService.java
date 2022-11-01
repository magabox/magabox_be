package com.clone.magabox.member.service;

import com.clone.magabox.member.dto.request.MemberRequestDto;
import com.clone.magabox.jwt.dto.request.TokenRequestDto;
import com.clone.magabox.member.dto.request.ValidatorIdDto;
import com.clone.magabox.dto.response.ResponseDto;
import com.clone.magabox.member.entity.ERole;
import com.clone.magabox.member.entity.Member;
import com.clone.magabox.jwt.entity.RefreshToken;
import com.clone.magabox.jwt.dto.response.TokenDto;
import com.clone.magabox.jwt.repository.RefreshTokenRepository;
import com.clone.magabox.jwt.security.JwtFilter;
import com.clone.magabox.jwt.security.TokenProvider;
import com.clone.magabox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;


    /*
     * 회원가입
     */

    @Transactional
    public ResponseDto<?> signup(MemberRequestDto memberRequestDto) {

        if (memberRepository.existsByUsername(memberRequestDto.getUsername()))
            throw new RuntimeException("중복된 아이디입니다.");

        Member member = new Member().builder()
                .username(memberRequestDto.getUsername())
                .name(memberRequestDto.getName())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .erole(ERole.ROLE_MEMBER)
                .build();

        memberRepository.save(member);

        return ResponseDto.success("회원가입 성공!");
    }

    /*
     * 로그인
     */
    @Transactional
    public ResponseDto<?> login(MemberRequestDto memberRequestDto, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword()))
            throw new RuntimeException("패스워드가 일치하지 않습니다.");

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.BEARER_PREFIX + tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.setHeader("User-Role", member.getErole().name());
        response.setHeader("username", member.getUsername());

        return ResponseDto.success("로그인 성공!");
    }

    /*
     * 중복확인
     */
    public ResponseDto<?> checkDuplicate(ValidatorIdDto validatorIdDto) {
        Member findMember = memberRepository.findByUsername(validatorIdDto.getUsername()).orElse(null);
        if(Objects.isNull(findMember)) {
            return ResponseDto.success(validatorIdDto.getUsername()+"는 가입할 수 있는 아이디입니다.");
        } else {
            return ResponseDto.fail(400,"중복된 아이디 입니다.",findMember.getUsername()+"는 중복된 아이디 입니다.");
        }
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh 토큰이 유효하지 않습니다.");
        }
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃된 사용자입니다."));
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }

}
