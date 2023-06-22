package com.study5.seoul.bike.controller;

import com.study5.seoul.bike.dto.MemberDto;
import com.study5.seoul.bike.dto.MemberLogin;
import com.study5.seoul.bike.dto.MemberRegistration;
import com.study5.seoul.bike.jwt.JwtProvider;
import com.study5.seoul.bike.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/seoul/bike")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    /** 회원가입 */
    @PostMapping("/member/register")
    public ResponseEntity<MemberRegistration.Response> register(
            @Valid @RequestBody MemberRegistration.Request request) {
        MemberDto memberDto = memberService.register(request);

        return new ResponseEntity<>(MemberRegistration.Response.from(memberDto), HttpStatus.CREATED);
    }

    /** 로그인 */
    @PostMapping("/member/login")
    public ResponseEntity<MemberLogin.Response> login(
            @RequestBody MemberLogin.Request request) {
        MemberDto memberDto = memberService.login(request);
        String accessToken = jwtProvider.GenerateAccessToken(memberDto.getEmail(), memberDto.getMemberRole());

        return new ResponseEntity<>(MemberLogin.Response.from(memberDto, accessToken), HttpStatus.OK);
    }

}
