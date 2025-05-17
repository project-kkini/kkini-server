package com.server.ggini.domain.member.domain;

import com.server.ggini.global.common.Coordinate;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyLocation {
    @Embedded
    private Coordinate coordinate;
    private Long nearestStationId;

    public CompanyLocation(Coordinate coordinate, Long nearestStationId) {
        this.coordinate = coordinate;
        this.nearestStationId = nearestStationId;
    }
}
