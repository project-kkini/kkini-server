package com.server.ggini.domain.review.dto.response;

import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.domain.review.domain.ReviewImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record ReviewGetDetailsResponse(
        Long id,
        String content,
        List<String> imageUrl,
        LocalDateTime createdAt
) {

    public static ReviewGetDetailsResponse from(Review review) {
        if (Objects.isNull(review)) {
            return null;
        }

        List<String> imageUrls = review.getImageUrls().stream()
                .map(ReviewImage::getUrl)
                .toList();

        return new ReviewGetDetailsResponse(
                review.getId(),
                review.getContent(),
                imageUrls,
                review.getCreatedDate()
        );
    }
}
