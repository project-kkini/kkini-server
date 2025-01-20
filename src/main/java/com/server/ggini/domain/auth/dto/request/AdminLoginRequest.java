package com.server.ggini.domain.auth.dto.request;

public record AdminLoginRequest(
        String email,
        String password
) {
}
