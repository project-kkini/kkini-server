package com.server.ggini.domain.locationAuth.dto;

public record IsSupportedAndSubwayStationDto(
        boolean isSupportedArea,
        SubwayStationDto subwayStation
) {
}
