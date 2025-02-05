package com.server.ggini.domain.member.controller;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.dto.request.LocalAuthRequest;
import com.server.ggini.domain.member.dto.response.LocalAuthResponse;
import com.server.ggini.global.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/localAuth")
@RequiredArgsConstructor
public class MemberLocalAuthController {

    @PostMapping("")
    @Operation(summary = "지역 인증 등록 - 미완", description = "지역 인증을 진행합니다.")
    public ResponseEntity<LocalAuthResponse> authenticateLocal(
            @AuthUser Member member,
            @RequestBody LocalAuthRequest localAuthRequest
    ){
        return ResponseEntity.ok(null);
    }

    @PutMapping("")
    @Operation(summary = "지역 인증 재등록 - 미완", description = "지역 인증을 재등록합니다.")
    public ResponseEntity<LocalAuthResponse> reAuthenticateLocal(
            @AuthUser Member member,
            @RequestBody LocalAuthRequest localAuthRequest
    ){
        return ResponseEntity.ok(null);
    }
}
