package com.server.ggini.domain.auth.dto.response.oauth;

import com.server.ggini.domain.auth.service.OAuthProvider;
import com.server.ggini.domain.member.domain.OauthInfo;
import lombok.Builder;

@Builder
public record OauthUserInfoResponse(
    String oauthId,
    String email,
    String name,
    OAuthProvider provider

) {

    public OauthInfo toEntity() {
        return OauthInfo.builder()
            .oauthId(this.oauthId())
            .oauthEmail(this.email())
            .name(this.name())
            .oauthProvider(this.provider().name())
            .build();
    }
}
