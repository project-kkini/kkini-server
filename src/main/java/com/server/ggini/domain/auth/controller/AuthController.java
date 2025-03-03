package com.server.ggini.domain.auth.controller;

import com.server.ggini.domain.auth.dto.request.AdminLoginRequest;
import com.server.ggini.domain.auth.dto.response.AccessTokenResponse;
import com.server.ggini.domain.auth.dto.response.LoginResponse;
import com.server.ggini.domain.auth.dto.response.MemberSignUpResponse;
import com.server.ggini.domain.auth.dto.response.TokenResponse;
import com.server.ggini.domain.auth.service.AuthService;
import com.server.ggini.domain.auth.service.TokenReissueService;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.NotFoundException;
import com.server.ggini.global.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDocs {

    private final TokenReissueService tokenReissueService;
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @Override
    @PostMapping("/oauth/social-login")
    public ResponseEntity<MemberSignUpResponse> socialLogin(
            @RequestHeader("social_access_token") String accessToken,
            @RequestParam("provider") String provider,
            HttpServletResponse response
    ) {
        LoginResponse loginResponse = authService.socialLogin(accessToken, provider);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", loginResponse.accessToken());
        
        Cookie refreshTokenCookie = cookieUtil.createCookie(loginResponse.refreshToken());
        response.addCookie(refreshTokenCookie);

        return new ResponseEntity<>(MemberSignUpResponse.of(loginResponse), headers, HttpStatus.OK);
    }

    @Override
    @PostMapping("/admin/login")
    public void adminLogin(@RequestBody AdminLoginRequest request) {
        // 실제 처리는 Security 필터에서 이루어지며, 이 메서드는 Swagger 명세용입니다.
    }

    @Override
    @GetMapping("/reissue")
    public ResponseEntity<Void> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = validateRefreshTokenCookie(request);

        TokenResponse tokenResponse = tokenReissueService.reissueToken(refreshToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenResponse.accessToken());
        response.addCookie(cookieUtil.createCookie(tokenResponse.refreshToken()));

        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }

    private static String validateRefreshTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new NotFoundException(ErrorCode.BLANK_INPUT_VALUE);
        }
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.BLANK_INPUT_VALUE));
    }
}
