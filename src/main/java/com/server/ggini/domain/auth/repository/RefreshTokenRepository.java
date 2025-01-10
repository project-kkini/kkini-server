package com.server.ggini.domain.auth.repository;

import com.server.ggini.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
