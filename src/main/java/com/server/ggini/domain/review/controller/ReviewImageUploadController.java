package com.server.ggini.domain.review.controller;

import com.server.ggini.domain.review.dto.response.PresignedUrlResponse;
import com.server.ggini.domain.review.service.ReviewImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ReviewImageUploadController {

    private final ReviewImageUploadService reviewImageUploadService;

    @Operation(summary = "이미지 업로드를 위한 presigned URL 발급", description = "url의 ? 쿼리 파라미터전까지가 이미지의 static url입니다.")
    @GetMapping("/review/{restaurantId}/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getReviewImagePresignedUrl(
            @PathVariable("restaurantId") Long restaurantId
    ) {
        String presignedUrl = reviewImageUploadService.generateReviewImagePresignedUrl(restaurantId);
        return ResponseEntity.ok(PresignedUrlResponse.of(presignedUrl));
    }

    @Operation(summary = "이미지 업로드 완료 후 URL 조회")
    @GetMapping("/{fileName}")
    public ResponseEntity<String> getImageUrl(
            @PathVariable("fileName") String fileName) {
        String imageUrl = reviewImageUploadService.getImageUrl(fileName);
        return ResponseEntity.ok(imageUrl);
    }
}
