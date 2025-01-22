package com.server.ggini.domain.auth.controller;

import com.server.ggini.domain.auth.dto.request.AdminLoginRequest;
import com.server.ggini.domain.auth.dto.response.LoginResponse;
import com.server.ggini.domain.auth.dto.response.MemberSignUpResponse;
import com.server.ggini.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "소셜 로그인으로 회원가입",
            description = "소셜 로그인 후 Authorization 토큰과 회원 정보를 발급합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원가입 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberSignUpResponse.class)
                            ),
                            headers = {
                                    @Header(name = "Authorization", description = "Access Token", schema = @Schema(type = "string")),
                                    @Header(name = "RefreshToken", description = "Refresh Token", schema = @Schema(type = "string"))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/oauth/social-login")
    public ResponseEntity<MemberSignUpResponse> socialLogin(
            @RequestHeader("social_access_token") String accessToken,
            @RequestParam("provider")
            @Parameter(example = "kakao", description = "OAuth 제공자")
            String provider
    ) {
        LoginResponse response = authService.socialLogin(accessToken, provider);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", response.accessToken());
        headers.set("RefreshToken", response.refreshToken());

        return new ResponseEntity<>(MemberSignUpResponse.of(response), headers,
                HttpStatus.OK);
    }

    @Operation(summary = "어드민 로그인", description = "아아디 패스워드 로그인 후 토큰 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    headers = {
                            @Header(name = "Authorization", description = "Access Token", schema = @Schema(type = "string")),
                            @Header(name = "RefreshToken", description = "Refresh Token", schema = @Schema(type = "string"))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/admin/login")
    public void adminLogin(
            @RequestBody AdminLoginRequest request
    ) {
        // 실제 처리는 Security 필터에서 이루어지며, 이 메서드는 Swagger 명세용입니다.
    }
}
