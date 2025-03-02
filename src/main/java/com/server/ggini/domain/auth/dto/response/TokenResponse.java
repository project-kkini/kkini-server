package com.server.ggini.domain.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
