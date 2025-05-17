package com.server.ggini.domain.locationAuth.domain;

import com.server.ggini.global.common.Coordinate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubwayStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subway_station_id")
    private Long id;

    private String name; // 역사명

    private String line; // 호선 정보

    private String district; // 지역구 정보

    @Embedded
    private Coordinate coordinate; // 위도, 경도
}
