package com.study5.seoul.bike.service;

import com.study5.seoul.bike.domain.Member;
import com.study5.seoul.bike.domain.MemberDetails;
import com.study5.seoul.bike.repository.MemberRepository;
import com.study5.seoul.bike.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));

        // 정지된 계정일 경우 예외 처리 TODO Error -> 수정 필요
        if (member.getMemberStatus() == MemberStatus.BLOCKED) {
            /* 정지된 계정입니다. */
            throw new RuntimeException(MemberStatus.BLOCKED.getDescription());
        }

        return new MemberDetails(member);
    }
}
