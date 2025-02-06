package com.server.ggini.domain.member.controller;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.dto.response.MemberGetResponse;
import com.server.ggini.global.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("me")
    @Operation(summary = "내 정보 조회", description = "jwt accessToken을 통해 내 정보를 조회합니다.")
    public ResponseEntity<MemberGetResponse> getMyInfo(
            @AuthUser Member member
            ) {
        return ResponseEntity.ok(MemberGetResponse.of(member));
    }

    @PutMapping("nickname")
    @Operation(summary = "닉네임 변경 - 미완", description = "jwt accessToken을 통해 내 닉네임을 변경합니다.")
    public ResponseEntity<MemberGetResponse> changeNickname(
            @AuthUser Member member,
            @RequestBody String nickname
    ) {
        return ResponseEntity.ok(null);
    }
}
