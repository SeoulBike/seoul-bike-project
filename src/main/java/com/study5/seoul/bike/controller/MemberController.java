package com.study5.seoul.bike.controller;

import com.study5.seoul.bike.dto.MemberLogin;
import com.study5.seoul.bike.dto.MemberRegistration;
import com.study5.seoul.bike.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/seoul/bike")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /** 회원가입 */
    @PostMapping("/member/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody MemberRegistration.Request request) {
        return ResponseEntity.status(CREATED).body(memberService.register(request));
    }

    /** 로그인 */
    @PostMapping("/member/login")
    public ResponseEntity<?> login(
            @RequestBody MemberLogin.Request request) {
        return ResponseEntity.ok().body(memberService.login(request));
    }

    /** 이메일 인증 */
    @PostMapping("/member/auth")
    public ResponseEntity<?> auth(
            @RequestParam Long memberId, @RequestParam String emailAuthKey) {
        memberService.verifyEmail(memberId, emailAuthKey);
        return ResponseEntity.ok().build();
    }
}
