package com.server.ggini.domain.locationAuth.controller;

import com.server.ggini.domain.locationAuth.service.LocationAuthService;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.locationAuth.dto.request.LocationAuthRequest;
import com.server.ggini.domain.locationAuth.dto.response.LocationAuthResponse;
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
public class LocationAuthController {
    private final LocationAuthService locationAuthService;

    @PostMapping("")
    @Operation(summary = "지역 인증 등록 - 미완", description = "지역 인증을 진행합니다.")
    public ResponseEntity<LocationAuthResponse> authenticateLocation(
            @AuthUser Member member,
            @RequestBody LocationAuthRequest locationAuthRequest
    ){
        return ResponseEntity.ok(locationAuthService.authenticateLocation(member, locationAuthRequest));
    }

    @PutMapping("")
    @Operation(summary = "지역 인증 재등록 - 미완", description = "지역 인증을 재등록합니다.")
    public ResponseEntity<LocationAuthResponse> reAuthenticateLocal(
            @AuthUser Member member,
            @RequestBody LocationAuthRequest locationAuthRequest
    ){
        return ResponseEntity.ok(null);
    }
}
