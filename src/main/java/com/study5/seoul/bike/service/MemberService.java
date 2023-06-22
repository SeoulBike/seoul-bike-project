package com.study5.seoul.bike.service;

import com.study5.seoul.bike.components.MailComponents;
import com.study5.seoul.bike.domain.Member;
import com.study5.seoul.bike.dto.MemberDto;
import com.study5.seoul.bike.dto.MemberLogin;
import com.study5.seoul.bike.dto.MemberRegistration;
import com.study5.seoul.bike.repository.MemberRepository;
import com.study5.seoul.bike.type.EmailVerificationStatus;
import com.study5.seoul.bike.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    /** 회원가입 */
    @Transactional
    public MemberDto register(MemberRegistration.Request request) {
        
        // TODO SMTP -> 속도 문제 해결 방법 필요

        /*
         * 1. 이메일 중복 체크
         * 2. 전화번호 중복 체크
         * 3. 닉네임 중복 체크
         * 4. 패스워드 암호화 후 저장
         */
        // 이메일 중복 체크
        memberRepository.findByEmail(request.getEmail()).ifPresent(member -> {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        });

        // 전화번호 중복 체크
        memberRepository.findByPhone(request.getPhone()).ifPresent(member -> {
            throw new RuntimeException("이미 사용중인 전화번호입니다.");
        });

        // 닉네임 중복 체크
        memberRepository.findByNickname(request.getNickname()).ifPresent(member -> {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        });

        // 암호화된 비밀번호
//        String encPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        String encPassword = passwordEncoder.encode(request.getPassword());

        // TODO uuid -> auth key (고민 해보기)
        String uuid = UUID.randomUUID().toString();

        Member member = memberRepository.save(Member.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .nickname(request.getNickname())
//                .password(request.getPassword())
                .password(encPassword)
                .memberStatus(MemberStatus.ACTIVE)
                .memberRole(request.getMemberRole())
                .registeredAt(LocalDateTime.now())

                // TODO 멘토링 질문
                .emailAuthKey(uuid)
                .emailVerificationStatus(EmailVerificationStatus.NOT_VERIFIED)
                
                .build());

        // 6자리 난수 (이메일 인증 번호)
        int random = (int) (Math.random() * 899999) + 100000;

        String email = request.getEmail();
        String subject = "따릉이 사이트 인증번호 안내";
        String text =
                "<p>따릉이 사이트 가입을 축하드립니다.<p>" +
                        "<p>아래 인증번호 확인 후 가입을 완료 하세요.</p>"
                        + "<div><h1>" + random + "</h1></div>";

        mailComponents.sendMail(email, subject, text);

        return MemberDto.fromEntity(member);
    }

    @Transactional
    public MemberDto login(MemberLogin.Request request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new RuntimeException("존재하지 않는 email 입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return MemberDto.fromEntity(member);
    }

}
