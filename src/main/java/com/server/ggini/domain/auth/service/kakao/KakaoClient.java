package com.server.ggini.domain.auth.service.kakao;

import com.server.ggini.domain.auth.dto.response.oauth.OauthUserInfoResponse;
import com.server.ggini.domain.auth.dto.response.oauth.kakao.KakaoAuthResponse;
import com.server.ggini.domain.auth.service.OAuthProvider;
import com.server.ggini.global.error.exception.AccessDeniedException;
import com.server.ggini.global.error.exception.ErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoClient {

    private final RestClient restClient;
    public static final String KAKAO_USER_ME_URL = "https://kapi.kakao.com/v2/user/me";
    public static final String TOKEN_PREFIX = "Bearer ";

    public OauthUserInfoResponse getOauthUserInfo(String token) {
        KakaoAuthResponse kakaoAuthResponse =
                restClient
                        .get()
                        .uri(KAKAO_USER_ME_URL)
                        .header("Authorization", TOKEN_PREFIX + token)
                        .exchange(
                                (request, response) -> {
                                    HttpStatusCode statusCode = response.getStatusCode();
                                    log.info("Received response with status: {}", statusCode);

                                    if (!response.getStatusCode().is2xxSuccessful()) {
                                        log.error("Kakao API error. Status: {}, Response: {}",
                                                statusCode, response.bodyTo(String.class));
                                        throw new AccessDeniedException(ErrorCode.KAKAO_TOKEN_CLIENT_FAILED);
                                    }
                                    return Objects.requireNonNull(
                                            response.bodyTo(KakaoAuthResponse.class));
                                });

        return new OauthUserInfoResponse(
                kakaoAuthResponse.id().toString(),
                kakaoAuthResponse.kakaoAccount().email(),
                kakaoAuthResponse.properties().nickname(),
                OAuthProvider.KAKAO
        );
    }
}
