package com.server.ggini.domain.locationAuth.controller;

import com.server.ggini.domain.locationAuth.service.LocationAuthService;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.locationAuth.dto.request.LocationAuthRequest;
import com.server.ggini.domain.locationAuth.dto.response.LocationAuthResponse;
import com.server.ggini.global.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequestMapping("/api/v1/locationAuth")
@RequiredArgsConstructor
public class LocationAuthController {
    private final LocationAuthService locationAuthService;

    @PostMapping("")
    @Operation(summary = "지역 인증 등록", description = "지역 인증을 진행합니다.")
    public ResponseEntity<LocationAuthResponse> authenticateLocation(
            @AuthUser Member member,
            @Valid @RequestBody LocationAuthRequest locationAuthRequest
    ){
        return ResponseEntity.ok(locationAuthService.authenticateLocation(member, locationAuthRequest));
    }

    @PutMapping("")
    @Operation(summary = "지역 인증 재등록 - 미완", description = "지역 인증을 재등록합니다.")
    public ResponseEntity<LocationAuthResponse> reAuthenticateLocal(
            @AuthUser Member member,
            @Valid @RequestBody LocationAuthRequest locationAuthRequest
    ){
        return ResponseEntity.ok(null);
    }
}
