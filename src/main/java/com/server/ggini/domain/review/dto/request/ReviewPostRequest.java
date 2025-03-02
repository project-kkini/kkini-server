package com.server.ggini.domain.review.dto.request;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.review.domain.Review;
import java.util.List;

public record ReviewPostRequest(
        String content,
        List<Long> needsTagIds,
        List<Long> priceTagIds,
        List<String> reviewImages
) {

    public Review toEntity(Member member, Restaurant restaurant, boolean isFirstReview) {
        return Review.builder()
                .content(content)
                .imageUrls(reviewImages)
                .reviewer(member)
                .restaurantId(restaurant.getId())
                .needsTagIds(needsTagIds)
                .priceTagIds(priceTagIds)
                .isFirstReview(isFirstReview)
                .build();
    }

}
