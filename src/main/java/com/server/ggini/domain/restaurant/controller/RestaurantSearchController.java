package com.server.ggini.domain.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantSearchController {

    @GetMapping("/search/count")
    @Operation(summary = "식당 검색 결과 개수 조회 - 미완", description = "해당 필터링으로 인한 식당 검색 결과 개수를 조회합니다.")
    public void getSearchCount(
            @RequestParam(value = "menuCategoryIds", required = false) List<Long> menuCategoryIds,
            @RequestParam(value = "priceTagIds", required = false) List<Long> priceTagIds,
            @RequestParam(value = "needsTagIds", required = false) List<Long> conceptTagIds
    ) {
    }

    @GetMapping("/search")
    @Operation(summary = "식당 검색 - 미완", description = "해당 필터링으로 인한 식당리스트를 검색합니다.")
    public void searchRestaurant(
            @RequestParam(value = "menuCategoryIds", required = false) List<Long> menuCategoryIds,
            @RequestParam(value = "priceTagIds", required = false) List<Long> priceTagIds,
            @RequestParam(value = "needsTagIds", required = false) List<Long> conceptTagIds
    ) {
    }
}
