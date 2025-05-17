package com.server.ggini.domain.restaurant.controller;

import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.dto.response.RestaurantGetSummaryResponse;
import com.server.ggini.domain.restaurant.dto.response.CursorBasedListResponse;
import com.server.ggini.domain.restaurant.service.RestaurantGetService;
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
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantSearchController {

    private final RestaurantGetService restaurantGetService;

    @GetMapping("/search/count")
    @Operation(summary = "식당 검색 결과 개수 조회", description = "해당 필터링으로 인한 식당 검색 결과 개수를 조회합니다.")
    public ResponseEntity<Long> getSearchCount(
            @RequestParam(value = "menuCategoryIds", required = false) List<Long> menuCategoryIds,
            @RequestParam(value = "priceTagIds", required = false) List<Long> priceTagIds,
            @RequestParam(value = "needsTagIds", required = false) List<Long> conceptTagIds
    ) {
        Long count = restaurantGetService.countSearchResults(menuCategoryIds, priceTagIds, conceptTagIds);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/search")
    @Operation(summary = "식당 검색", description = "해당 필터링으로 인한 식당리스트를 검색합니다.")
    public ResponseEntity<CursorBasedListResponse<RestaurantGetSummaryResponse>> searchRestaurant(
            @RequestParam(value = "menuCategoryIds", required = false) List<Long> menuCategoryIds,
            @RequestParam(value = "priceTagIds", required = false) List<Long> priceTagIds,
            @RequestParam(value = "needsTagIds", required = false) List<Long> conceptTagIds,
            @RequestParam(value = "cursor", required = false) String cursor,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        CursorBasedListResponse<RestaurantGetSummaryResponse> response = restaurantGetService.searchRestaurants(
            menuCategoryIds,
            priceTagIds,
            conceptTagIds,
            cursor,
            size
        );

        return ResponseEntity.ok(response);
    }
}
