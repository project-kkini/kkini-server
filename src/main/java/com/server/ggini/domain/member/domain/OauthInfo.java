package com.server.ggini.domain.member.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthInfo {

    public String oauthId;
    private String oauthProvider;
    private String oauthEmail;
    private String name;

    @Builder
    public OauthInfo(String oauthId, String oauthProvider, String oauthEmail, String name) {
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.oauthEmail = oauthEmail;
        this.name = name;
    }
}
