package com.study5.seoul.bike.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailVerificationStatus {

    NOT_VERIFIED("이메일 인증 진행 중입니다."),
    VERIFIED("이메일 인증 진행 완료");

    private String description;

}

