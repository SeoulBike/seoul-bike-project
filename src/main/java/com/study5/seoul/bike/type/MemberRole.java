package com.study5.seoul.bike.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    USER("ROLE_USER", "일반 회원")
    , ADMIN("ROLE_ADMIN", "어드민 회원")

    ;

    private final String authority;
    private final String description;
}
