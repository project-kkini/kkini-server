package com.server.ggini.domain.auth.dto.response;

import com.server.ggini.domain.member.domain.Member;

public record LoginResponse(
    Long memberId,
    String nickname,
    String email,
    String accessToken,
    String refreshToken
) {

    public static LoginResponse of(Member member, TokenPairResponse tokenPairResponse) {
        return new LoginResponse(member.getId(), member.getNickname(), member.getOauthInfo().getOauthEmail(), tokenPairResponse.accessToken(), tokenPairResponse.refreshToken());
    }
}
