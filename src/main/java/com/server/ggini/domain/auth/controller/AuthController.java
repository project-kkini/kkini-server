package com.server.ggini.domain.auth.controller;

import com.server.ggini.domain.auth.dto.response.LoginResponse;
import com.server.ggini.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "소셜 로그인으로 회원가입", description = "소셜 로그인 후 토큰과 회원 정보를 발급합니다.")
    @PostMapping("/oauth/social-login")
    public ResponseEntity<LoginResponse> socialLogin(
            @RequestHeader("social_access_token") String accessToken,
            @RequestParam("provider")
            @Parameter(example = "kakao", description = "OAuth 제공자")
            String provider
    ) {
        LoginResponse response = authService.socialLogin(accessToken, provider);
        return ResponseEntity.ok(response);
    }
}
