package com.server.ggini.domain.locationAuth.repository;

import com.server.ggini.domain.locationAuth.domain.SubwayStation;
import com.server.ggini.domain.locationAuth.dto.SubwayStationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubwayStationRepository extends JpaRepository<SubwayStation, Long> {

    @Query(value =
            "SELECT s.subway_station_id AS subwayStationId, s.name AS subwayStationName " +
                    "FROM subway_station s " +
                    "ORDER BY ST_Distance_Sphere(point(s.longitude, s.latitude), point(:longitude, :latitude)) " +
                    "LIMIT 1", nativeQuery = true)
    Optional<SubwayStationDto> findNearestStation(@Param("latitude") double latitude, @Param("longitude") double longitude);

}
