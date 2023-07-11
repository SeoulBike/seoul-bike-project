package com.study5.seoul.bike.service;

import com.study5.seoul.bike.dto.MemberLogin;
import com.study5.seoul.bike.dto.MemberRegistration;

public interface MemberService {

    // 회원가입
    MemberRegistration.Response register(MemberRegistration.Request request);

    // 로그인
    MemberLogin.Response login(MemberLogin.Request request);

    // 인증
    void verifyEmail(Long id, String emailAuthKey);
}
