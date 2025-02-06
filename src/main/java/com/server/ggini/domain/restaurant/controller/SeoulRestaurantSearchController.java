package com.server.ggini.domain.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seoulRestaurants")
@RequiredArgsConstructor
public class SeoulRestaurantSearchController {

    @GetMapping("/search/count")
    @Operation(summary = "추천 가능 식당 조회 - 미완", description = "식당 이름과 현재 위치로 서울시 인증 식당 DB에 저장된 식당을 조회합니다.")
    public void getSearchCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude
    ) {
    }
}
