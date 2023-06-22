package com.study5.seoul.bike.dto;

import com.study5.seoul.bike.type.MemberRole;
import com.study5.seoul.bike.type.MemberStatus;
import lombok.Builder;
import lombok.Getter;

public class MemberLogin {

    @Getter
    @Builder
    public static class Request {

        private String email;
        private String password;
    }

    @Getter
    @Builder
    public static class Response {

        private Long id;
        private String email;
        private String phone;
        private String nickname;

        private MemberStatus memberStatus;
        private MemberRole memberRole;

        private String accessToken;

        public static Response from(MemberDto memberDto, String accessToken) {
            return Response.builder()
                    .id(memberDto.getId())
                    .email(memberDto.getEmail())
                    .phone(memberDto.getPhone())
                    .nickname(memberDto.getNickname())
                    .memberStatus(memberDto.getMemberStatus())
                    .memberRole(memberDto.getMemberRole())
                    .accessToken(accessToken)
                    .build();
        }

    }
}
