package com.server.ggini.domain.auth.controller;

import com.server.ggini.domain.auth.dto.response.MemberSignUpResponse;
import com.server.ggini.domain.auth.dto.request.AdminLoginRequest;
import com.server.ggini.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증", description = "소셜 로그인 인증 관련 API")
public interface AuthControllerDocs {

    @SecurityRequirements(value = {})
    @Operation(
            summary = "소셜 로그인",
            description = "소셜 액세스 토큰으로 로그인하여 자체 액세스 토큰을 발급받고 리프레시 토큰을 쿠키에 설정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberSignUpResponse.class)
                            ),
                            headers = {
                                    @Header(
                                            name = "Authorization",
                                            description = "발급된 액세스 토큰",
                                            schema = @Schema(type = "string")
                                    ),
                                    @Header(
                                            name = "Set-Cookie",
                                            description = "리프레시 토큰이 담긴 HTTP Only 쿠키",
                                            schema = @Schema(
                                                    type = "string",
                                                    example = "refreshToken=xxx; Path=/; HttpOnly; Secure; SameSite=None"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "유효하지 않은 소셜 액세스 토큰",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ResponseEntity<MemberSignUpResponse> socialLogin(
            String accessToken,
            String provider
    );

    @Operation(
            summary = "어드민 로그인",
            description = "아이디 패스워드 로그인 후 토큰 발급합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            headers = {
                                    @Header(
                                            name = "Authorization",
                                            description = "Access Token",
                                            schema = @Schema(type = "string")
                                    ),
                                    @Header(
                                            name = "Set-Cookie",
                                            description = "리프레시 토큰이 담긴 HTTP Only 쿠키",
                                            schema = @Schema(
                                                    type = "string",
                                                    example = "refreshToken=xxx; Path=/; HttpOnly; Secure; SameSite=None"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            })
    void adminLogin(AdminLoginRequest request);
} 