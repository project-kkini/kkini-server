package com.server.ggini.domain.locationAuth.dto.request;

import jakarta.validation.constraints.NotNull;

public record LocationAuthRequest(
        @NotNull(message = "위도는 필수 입력값입니다.")
        Double latitude,
        @NotNull(message = "경도는 필수 입력값입니다.")
        Double longitude
) {
}
