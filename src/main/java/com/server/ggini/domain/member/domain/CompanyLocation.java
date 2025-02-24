package com.server.ggini.domain.member.domain;

import com.server.ggini.global.common.Coordinate;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyLocation {
    @Embedded
    private Coordinate coordinate;
    private Long nearestStationId;
}
