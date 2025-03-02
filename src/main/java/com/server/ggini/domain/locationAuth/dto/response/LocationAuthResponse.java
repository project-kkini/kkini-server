package com.server.ggini.domain.locationAuth.dto.response;

public record LocationAuthResponse(
        boolean isSupportedArea,
        String subwayStationName
) {
}
