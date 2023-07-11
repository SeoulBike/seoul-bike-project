package com.study5.seoul.bike.service.impl;

import com.study5.seoul.bike.components.MailComponents;
import com.study5.seoul.bike.domain.Member;
import com.study5.seoul.bike.domain.RefreshToken;
import com.study5.seoul.bike.dto.MemberLogin;
import com.study5.seoul.bike.dto.MemberRegistration;
import com.study5.seoul.bike.dto.TokenDto;
import com.study5.seoul.bike.jwt.JwtProvider;
import com.study5.seoul.bike.repository.MemberRepository;
import com.study5.seoul.bike.repository.RefreshTokenRepository;
import com.study5.seoul.bike.service.MemberService;
import com.study5.seoul.bike.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.study5.seoul.bike.type.EmailVerificationStatus.NOT_VERIFIED;
import static com.study5.seoul.bike.type.MemberStatus.INACTIVE;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailComponents mailComponents;
    private final JwtProvider jwtProvider;

    /** 회원가입 */
    @Override
    @Transactional
    public MemberRegistration.Response register(MemberRegistration.Request request) {
        // 1. 유효성 검사(이메일, 전화번호, 닉네임 중복 체크)
        validateEmailDuplication(request);
        validatePhoneDuplication(request);
        validateNickNameDuplication(request);

        String encPassword = passwordEncoder.encode(request.getPassword());
        String emailAuthKey = CommonUtils.generateRandomCode();

        // 2. 인증코드 전송
        sendRegistrationConfirmEmail(request.getEmail(), emailAuthKey);

        // 3. 회원가입
        return MemberRegistration.Response.fromEntity(memberRepository.save(
                Member.builder()
                        .email(request.getEmail())
                        .phone(request.getPhone())
                        .nickname(request.getNickname())
                        .password(encPassword)
                        .memberStatus(INACTIVE)
                        .memberRole(request.getMemberRole())
                        .emailAuthKey(emailAuthKey)
                        .emailVerificationStatus(NOT_VERIFIED)
                        .build()));
    }

    /** 로그인 */
    @Override
    @Transactional
    public MemberLogin.Response login(MemberLogin.Request request) {
        // 1. 유효성 검사(이메일 확인, 비밀번호 일치 확인)
        Member member = getMemberByEmail(request.getEmail());
        validatePassword(request.getPassword(), member.getPassword());

        // 2. 토큰 발급
        TokenDto tokenDto = generateToken(member);
        String refreshToken = tokenDto.getRefreshToken();
        Long memberId = member.getId();

        // 3. 리프레시 토큰 저장
        RefreshToken currentToken = refreshTokenRepository.findByKey(memberId).orElse(null);
        if (currentToken != null) {
            currentToken.updateToken(refreshToken);
            refreshTokenRepository.save(currentToken);
        } else {
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .key(memberId)
                            .token(refreshToken)
                            .build());
        }

        return MemberLogin.Response.builder()
                .id(memberId)
                .token(tokenDto)
                .build();
    }

    /** 이메일 인증 */
    @Override
    public void verifyEmail(Long id, String emailAuthKey) {
        // 1. 유효성 검사(회원 조회, 인증번호 일치 확인)
        Member member = getMemberById(id);
        validateEmailAuthKey(member.getEmailAuthKey(), emailAuthKey);

        // 2. 이메일 인증
        member.confirmEmailVerification();
        memberRepository.save(member);
    }

    private void validateEmailDuplication(MemberRegistration.Request request) {
        memberRepository.findByEmail(request.getEmail()).ifPresent(member -> {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        });
    }

    private void validatePhoneDuplication(MemberRegistration.Request request) {
        memberRepository.findByPhone(request.getPhone()).ifPresent(member -> {
            throw new RuntimeException("이미 사용중인 전화번호입니다.");
        });
    }

    private void validateNickNameDuplication(MemberRegistration.Request request) {
        memberRepository.findByNickname(request.getNickname()).ifPresent(member -> {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        });
    }

    private void sendRegistrationConfirmEmail(String email, String authKey) {
        String title = "따릉이 사이트 인증번호 안내";
        String text =
                "<p>따릉이 사이트 가입을 축하드립니다.<p>" +
                        "<p>아래 인증번호 확인 후 가입을 완료 하세요.</p>"
                        + "<div><h1>" + authKey + "</h1></div>";

        mailComponents.sendMail(email, title, text);
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
    }

    private void validatePassword(String inputPassword, String encodedPassword) {
        if (!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    private static void validateEmailAuthKey(String memberEmailAuthKey, String emailAuthKey) {
        if (!Objects.equals(memberEmailAuthKey, emailAuthKey)) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }
    }

    private TokenDto generateToken(Member member) {
        return jwtProvider.GenerateToken(member.getPhone(), member.getMemberRole());
    }

}

