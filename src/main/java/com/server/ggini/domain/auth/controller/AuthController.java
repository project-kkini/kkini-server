package com.server.ggini.domain.auth.controller;

import com.server.ggini.domain.auth.dto.request.AdminLoginRequest;
import com.server.ggini.domain.auth.dto.response.LoginResponse;
import com.server.ggini.domain.auth.dto.response.MemberSignUpResponse;
import com.server.ggini.domain.auth.service.AuthService;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.global.security.AuthConstants;
import com.server.ggini.global.security.utils.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @Override
    @PostMapping("/oauth/social-login")
    public ResponseEntity<MemberSignUpResponse> socialLogin(
            @RequestHeader("social_access_token") String accessToken,
            @RequestParam("provider") String provider
    ) {
        LoginResponse response = authService.socialLogin(accessToken, provider);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", response.accessToken());
        
        Cookie refreshTokenCookie = cookieUtil.createCookie(response.refreshToken());
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return new ResponseEntity<>(MemberSignUpResponse.of(response), headers, HttpStatus.OK);
    }

    @Override
    @PostMapping("/admin/login")
    public void adminLogin(@RequestBody AdminLoginRequest request) {
        // 실제 처리는 Security 필터에서 이루어지며, 이 메서드는 Swagger 명세용입니다.
    }
}
