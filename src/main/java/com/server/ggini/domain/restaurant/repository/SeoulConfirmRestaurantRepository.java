package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.SeoulConfirmRestaurant;
import com.server.ggini.domain.restaurant.dto.response.SeoulConfirmRestaurantGetResponse;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeoulConfirmRestaurantRepository extends JpaRepository<SeoulConfirmRestaurant, Long> {

    default SeoulConfirmRestaurant findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.SEOUL_RESTAURANT_NOT_FOUND));
    }

    // 반경 내에 해당 검색어를 포함하는 음식점이 존재하는 지 조사
    @Query(nativeQuery = true,
            value = "SELECT id, name, road_address AS roadAddress, " +
                    "ROUND(ST_Distance_Sphere(POINT(longitude, latitude), POINT(:userLng, :userLat))) AS distance " +
                    "FROM seoul_confirm_restaurant " +
                    "WHERE name LIKE CONCAT('%', :searchKeyword, '%') " +
                    "AND ST_Distance_Sphere(POINT(longitude, latitude), POINT(:userLng, :userLat)) <= :radius " +
                    "ORDER BY " +
                    "CASE " +
                    "    WHEN name LIKE CONCAT(:searchKeyword, '%') THEN 1 " +
                    "    WHEN name LIKE CONCAT('%', :searchKeyword, '%') THEN 2 " +
                    "END, name")
    List<SeoulConfirmRestaurantGetResponse> findRestaurantsByNameAndLocation(
            @Param("searchKeyword") String searchKeyword,
            @Param("userLat") double latitude,
            @Param("userLng") double longitude,
            @Param("radius") double radius);


    // 700m 밖의 음식점이 존재하는지 확인
    @Query(nativeQuery = true,
        value = "SELECT EXISTS(SELECT 1 FROM seoul_confirm_restaurant " +
            "WHERE name LIKE CONCAT('%', :searchKeyword, '%') " +
            "AND ST_Distance_Sphere(POINT(longitude, latitude), POINT(:userLng, :userLat)) > :radius)")
    boolean existsRestaurantOutsideRadius(
        @Param("searchKeyword") String searchKeyword,
        @Param("userLat") double latitude,
        @Param("userLng") double longitude,
        @Param("radius") double radius);
}
