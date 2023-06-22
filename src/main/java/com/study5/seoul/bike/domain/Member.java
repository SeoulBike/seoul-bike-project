package com.study5.seoul.bike.domain;

import com.study5.seoul.bike.type.EmailVerificationStatus;
import com.study5.seoul.bike.type.MemberRole;
import com.study5.seoul.bike.type.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {

    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    // TODO 이메일 관련 작업
    @Enumerated(EnumType.STRING)
    private EmailVerificationStatus emailVerificationStatus;
    private String emailAuthKey;
    private LocalDateTime emailAuthAt;

}
