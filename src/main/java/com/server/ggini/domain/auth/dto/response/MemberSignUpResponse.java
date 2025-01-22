package com.server.ggini.domain.auth.dto.response;

public record MemberSignUpResponse(
    Long memberId,
    String nickname,
    String email
) {

    public static MemberSignUpResponse of(LoginResponse loginResponse) {
        return new MemberSignUpResponse(loginResponse.memberId(), loginResponse.nickname(), loginResponse.email());
    }
}
