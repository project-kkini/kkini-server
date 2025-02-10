package com.server.ggini.domain.restaurant.controller;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.dto.response.RestaurantGetDetailsResponse;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.restaurant.service.RestaurantGetService;
import com.server.ggini.global.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRepository seoulRestaurantRepository;
    private final RestaurantGetService restaurantGetService;

    @GetMapping("{seoulRestaurantId}/registered")
    @Operation(
            summary = "끼니에 등록된 식당인지 조회",
            description = "서울시 승인 식당 id를 통해 해당 식당이 끼니 시스템에 등록되어 있는지 조회합니다."
    )
    public ResponseEntity<Long> getRestaurantBySeoulRestaurantId(
            @PathVariable("seoulRestaurantId") Long seoulRestaurantId
    ) {
        return ResponseEntity.ok(seoulRestaurantRepository.findBySeoulRestaurantIdOrThrow(seoulRestaurantId).getId());
    }

    @GetMapping("{restaurantId}")
    @Operation(summary = "식당 정보 상세 조회", description = "식당 id를 통해 해당 식당을 조회합니다.")
    public ResponseEntity<RestaurantGetDetailsResponse> getRestaurant(
            @PathVariable("restaurantId") Long restaurantId,
            @AuthUser Member member
            ) {
        return ResponseEntity.ok(restaurantGetService.getRestaurant(restaurantId, member));
    }
}
