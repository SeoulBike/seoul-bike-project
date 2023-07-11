package com.study5.seoul.bike.dto;

import com.study5.seoul.bike.domain.Member;
import com.study5.seoul.bike.type.EmailVerificationStatus;
import com.study5.seoul.bike.type.MemberRole;
import com.study5.seoul.bike.type.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String email;
    private String phone;
    private String nickname;
    private String password;

    private MemberStatus memberStatus;
    private MemberRole memberRole;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    private EmailVerificationStatus emailVerificationStatus;
    private String emailAuthKey;
    private LocalDateTime emailAuthAt;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .phone(member.getPhone())
                .nickname(member.getNickname())
                .memberStatus(member.getMemberStatus())
                .memberRole(member.getMemberRole())
                .emailVerificationStatus(member.getEmailVerificationStatus())
                .emailAuthKey(member.getEmailAuthKey())
                .emailAuthAt(member.getEmailAuthAt())
                .build();
    }
}
