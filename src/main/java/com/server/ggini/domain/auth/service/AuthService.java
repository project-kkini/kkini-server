package com.server.ggini.domain.auth.service;

import com.server.ggini.domain.auth.dto.response.LoginResponse;
import com.server.ggini.domain.auth.dto.response.TokenPairResponse;
import com.server.ggini.domain.auth.dto.response.oauth.OauthUserInfoResponse;
import com.server.ggini.domain.auth.service.kakao.KakaoClient;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.domain.OauthInfo;
import com.server.ggini.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse socialLogin(String socialAccessToken, String provider) {
        OauthUserInfoResponse oauthUserInfoResponse = getOauthUserInfo(socialAccessToken, OAuthProvider.from(provider));
        OauthInfo oauthInfo = oauthUserInfoResponse.toEntity();

        Member member = memberService.getMemberByOAuthInfo(oauthInfo);

        TokenPairResponse tokenPairResponse = jwtTokenProvider.generateTokenPair(member);
        return LoginResponse.of(member, tokenPairResponse);
    }

    private OauthUserInfoResponse getOauthUserInfo(String socialAccessToken, OAuthProvider provider) {
        return switch (provider) {
            case APPLE -> null; //TODO: Apple OAuth
            case KAKAO -> kakaoClient.getOauthUserInfo(socialAccessToken);
        };
    }
}
