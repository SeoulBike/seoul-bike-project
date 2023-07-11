package com.study5.seoul.bike.repository;

import com.study5.seoul.bike.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 유저 ID를 통해 토큰 검색
    Optional<RefreshToken> findByKey(Long key);
}