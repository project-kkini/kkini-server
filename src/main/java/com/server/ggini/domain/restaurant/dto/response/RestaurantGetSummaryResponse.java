package com.server.ggini.domain.restaurant.dto.response;

import com.server.ggini.domain.review.dto.response.ReviewGetDetailsResponse;
import java.util.List;

public record RestaurantGetSummaryResponse(
        Long id,
        String name,
        String address,
        String menuCategory,
        Long reviewCount,
        List<PriceTagGetResponse> priceTags,
        List<String> imageUrls,
        List<ReviewGetDetailsResponse> reviews
) {
}
