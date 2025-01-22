package com.server.ggini.domain.member.controller;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.dto.response.MemberGetResponse;
import com.server.ggini.global.annotation.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("me")
    public ResponseEntity<MemberGetResponse> getMyInfo(
            @AuthUser Member member
            ) {
        return ResponseEntity.ok(MemberGetResponse.of(member));
    }
}
