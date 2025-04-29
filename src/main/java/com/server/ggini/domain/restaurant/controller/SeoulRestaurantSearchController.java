package com.server.ggini.domain.restaurant.controller;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.dto.response.RestaurantsNearbyGetResponse;
import com.server.ggini.domain.restaurant.dto.response.SeoulConfirmRestaurantGetResponse;
import com.server.ggini.domain.restaurant.service.SeoulConfirmRestaurantService;
import com.server.ggini.global.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "식당", description = "식당 관련 API")
@RestController
@RequestMapping("/api/v1/seoulRestaurants")
@RequiredArgsConstructor
public class SeoulRestaurantSearchController {

    private final SeoulConfirmRestaurantService seoulConfirmRestaurantService;

    @GetMapping("/search")
    @Operation(summary = "추천 가능 식당 조회", description = "식당 이름과 회사 위치로 서울시 인증 식당 DB에 저장된 식당을 조회합니다."
        + "status = (FOUND_WITHIN_RADIUS/ FOUND_OUTSIDE_RADIUS / NOT_FOUND)")
    public ResponseEntity<RestaurantsNearbyGetResponse> findRestaurantsNearby(
            @AuthUser Member member,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        // TODO: 회사 위치 매개변수로 안 받고, 테이블에 저장되어있는 거에서 빼서 쓰기
        return ResponseEntity.ok(seoulConfirmRestaurantService.findRestaurantsNearby(member, keyword, latitude, longitude, 700, page));
    }
}
