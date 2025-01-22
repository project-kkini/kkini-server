package com.server.ggini.domain.member.dto.response;

import com.server.ggini.domain.member.domain.Member;

public record MemberGetResponse(
        Long id,
        String nickname,
        String email
) {
    public static MemberGetResponse of(Member member) {
        String email = member.getEmail();
        if (member.getOauthInfo() != null && member.getOauthInfo().getOauthEmail() != null) {
            email = member.getOauthInfo().getOauthEmail();
        }

        return new MemberGetResponse(
                member.getId(),
                member.getNickname(),
                email
        );
    }
}
