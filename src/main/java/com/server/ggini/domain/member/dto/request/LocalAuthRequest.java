package com.server.ggini.domain.member.dto.request;

public record LocalAuthRequest(
        String latitude,
        String longitude
) {
}
