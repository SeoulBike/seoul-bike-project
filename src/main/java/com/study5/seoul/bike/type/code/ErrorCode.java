package com.study5.seoul.bike.type.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    ;

    private final int status;
    private final String code;
    private final String message;
}
