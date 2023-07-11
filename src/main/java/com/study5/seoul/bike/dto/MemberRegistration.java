package com.study5.seoul.bike.dto;

import com.study5.seoul.bike.domain.Member;
import com.study5.seoul.bike.type.MemberRole;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberRegistration {

    @Getter
    @Builder
    public static class Request {

        @NotBlank(message = "이메일이 누락되었습니다.")
        @Email(message = "이메일 주소 형식이 잘못되었습니다.")
        private String email;
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$"
                , message = "전화번호 형식이 맞지 않습니다.(01X-XXX(X)-XXXX)")
        private String phone;
        @NotBlank(message = "닉네임이 누락되었습니다.")
        @Pattern(regexp = "^(?!\\s*$).+", message = "닉네임에 공백이 포함될 수 없습니다.")
        private String nickname;
        @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$"
                , message = "비밀번호는 영어와 숫자를 혼용해야 하며 공백은 사용할 수 없습니다.")
        private String password;
        private MemberRole memberRole;
    }

    @Getter
    @Builder
    public static class Response {

        private Long id;
        private String emailAuthKey;

        public static Response fromEntity(Member member) {
            return Response.builder()
                    .id(member.getId())
                    .emailAuthKey(member.getEmailAuthKey())
                    .build();
        }
    }
}
