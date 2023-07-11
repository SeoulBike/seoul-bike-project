package com.study5.seoul.bike.repository;


import com.study5.seoul.bike.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String phone);
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByNickname(String nickname);
}
