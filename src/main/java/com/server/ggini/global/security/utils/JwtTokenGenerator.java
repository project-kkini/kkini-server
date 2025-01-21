package com.server.ggini.global.security.utils;

import com.server.ggini.domain.auth.domain.RefreshToken;
import com.server.ggini.domain.auth.dto.response.TokenPairResponse;
import com.server.ggini.domain.auth.repository.RefreshTokenRepository;
import com.server.ggini.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenPairResponse generateTokenPair(Member member) {
        String accessToken = createAccessToken(member);
        String refreshToken = createRefreshToken(member);
        return TokenPairResponse.of(accessToken, refreshToken);
    }

    private String createAccessToken(Member member) {
        return jwtUtil.generateAccessToken(member);
    }

    private String createRefreshToken(Member member) {
        String token = jwtUtil.generateRefreshToken(member);
        saveRefreshToken(member.getId(), token);
        return token;
    }

    private void saveRefreshToken(Long memberId, String refreshToken) {
        if(refreshTokenRepository.existsByMemberId(memberId)) {
            refreshTokenRepository.deleteByMemberId(memberId);
        }
        refreshTokenRepository.save(RefreshToken.builder()
                .memberId(memberId)
                .token(refreshToken)
                .build());
    }
}
