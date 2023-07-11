package com.study5.seoul.bike.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static com.study5.seoul.bike.type.EmailVerificationStatus.VERIFIED;
import static com.study5.seoul.bike.type.MemberStatus.ACTIVE;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        /* 권한 -> 일반 회원, 어드민 회원 */
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getMemberRole().getRole());
        return Collections.singleton(grantedAuthority);
    }

    // 사용자 암호화된 비밀번호를 반환
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 사용자 고유 식별자인 이메일 반환
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    // 사용자 계정 만료여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정이 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자 인증 정보(비밀번호)가 만료되었는지 여부를 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return member.getEmailVerificationStatus() == VERIFIED;
    }

    // 사용자 계정 사용 가능여부 확인
    @Override
    public boolean isEnabled() {
        return member.getMemberStatus() == ACTIVE;
    }
}
