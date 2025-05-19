package com.server.ggini.domain.restaurant.controller;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.dto.response.RestaurantsNearbyGetResponse;
import com.server.ggini.domain.restaurant.dto.response.SeoulConfirmRestaurantGetResponse;
import com.server.ggini.domain.restaurant.service.SeoulConfirmRestaurantService;
import com.server.ggini.global.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        + "status = (FOUND_WITHIN_RADIUS : 700m 이내에 음식점이 있는 경우 / FOUND_OUTSIDE_RADIUS: 음식점은 있지만 700m 이내에 없는 경우 / NOT_FOUND: 검색어와 일치하는 음식점이 없는 경우)")
    public ResponseEntity<RestaurantsNearbyGetResponse> findRestaurantsNearby(
            @AuthUser Member member,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {
        return ResponseEntity.ok(seoulConfirmRestaurantService.findRestaurantsNearby(member, keyword, 700, page));
    }
}
