package com.server.ggini.domain.review.controller;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.dto.response.RestaurantGetDetailsResponse;
import com.server.ggini.domain.review.dto.request.ReviewPostRequest;
import com.server.ggini.domain.review.service.ReviewCreateService;
import com.server.ggini.domain.review.service.ReviewFirstCreateService;
import com.server.ggini.global.annotation.AuthUser;
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

    private final ReviewFirstCreateService reviewFirstCreateService;
    private final ReviewCreateService reviewCreateService;

    @PostMapping("/restaurants/{restaurantId}/review")
    @Operation(summary = "기존 끼니 식당에 리뷰 등록", description = "이미 끼니에 리뷰가 하나 이상 존재하는 식당에 리뷰를 등록합니다.")
    public ResponseEntity<Long> addReview(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody ReviewPostRequest request,
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(reviewCreateService.createReview(restaurantId, request, member));
    }

    @PostMapping("/newRestaurants/{seoulRestaurantId}/review")
    @Operation(summary = "새로운 식당 리뷰 등록", description = "끼니에 리뷰가 존재하지 않는 새로운 식당에 리뷰를 등록합니다.")
    public ResponseEntity<Long> addNewRestaurantReview(
            @PathVariable("seoulRestaurantId") Long seoulRestaurantId,
            @RequestBody ReviewPostRequest request,
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(reviewFirstCreateService.createReviewFirst(seoulRestaurantId, request, member));
    }
}
