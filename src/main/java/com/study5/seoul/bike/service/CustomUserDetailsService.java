package com.study5.seoul.bike.service;

import com.study5.seoul.bike.domain.CustomUserDetails;
import com.study5.seoul.bike.domain.Member;
import com.study5.seoul.bike.repository.MemberRepository;
import com.study5.seoul.bike.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.study5.seoul.bike.type.MemberStatus.BLOCKED;
import static com.study5.seoul.bike.type.MemberStatus.INACTIVE;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Member member = memberRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        MemberStatus memberStatus = member.getMemberStatus();
        if (memberStatus == INACTIVE) {
            throw new RuntimeException(INACTIVE.getDescription());
        } else if (memberStatus == BLOCKED) {
            throw new RuntimeException(BLOCKED.getDescription());
        }

        return new CustomUserDetails(member);
    }
}
