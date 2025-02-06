package com.server.ggini.domain.review.controller;

import com.server.ggini.domain.restaurant.dto.response.RestaurantGetDetailsResponse;
import com.server.ggini.domain.review.dto.request.ReviewPostRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping("/restaurants/{restaurantId}/review")
    @Operation(summary = "기존 끼니 식당에 리뷰 등록 - 미완", description = "이미 끼니에 리뷰가 하나 이상 존재하는 식당에 리뷰를 등록합니다.")
    public ResponseEntity<RestaurantGetDetailsResponse> addReview(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody ReviewPostRequest request
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/newRestaurants/{seoulRestaurantId}/review")
    @Operation(summary = "새로운 식당 리뷰 등록 - 미완", description = "끼니에 리뷰가 존재하지 않는 새로운 식당에 리뷰를 등록합니다.")
    public ResponseEntity<RestaurantGetDetailsResponse> addNewRestaurantReview(
            @PathVariable("seoulRestaurantId") Long seoulRestaurantId,
            @RequestBody ReviewPostRequest request
    ) {
        return ResponseEntity.ok(null);
    }
}
