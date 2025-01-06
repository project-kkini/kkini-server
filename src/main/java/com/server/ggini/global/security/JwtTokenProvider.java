package com.server.ggini.global.security;

import com.server.ggini.domain.auth.domain.RefreshToken;
import com.server.ggini.domain.auth.dto.response.TokenPairResponse;
import com.server.ggini.domain.auth.repository.RefreshTokenRepository;
import com.server.ggini.domain.member.domain.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    public TokenPairResponse generateTokenPair(Long memberId, MemberRole memberRole) {
        String accessToken = createAccessToken(memberId, memberRole);
        String refreshToken = createRefreshToken(memberId, memberRole);
        return TokenPairResponse.of(accessToken, refreshToken);
    }

    private String createAccessToken(Long memberId, MemberRole memberRole) {
        return jwtUtil.generateAccessToken(memberId, memberRole);
    }

    private String createRefreshToken(Long memberId, MemberRole memberRole) {
        String token = jwtUtil.generateRefreshToken(memberId, memberRole);
        saveRefreshToken(memberId, token);
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
