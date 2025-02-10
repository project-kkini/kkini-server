package com.server.ggini.domain.restaurant.dto.response;

import com.server.ggini.domain.restaurant.domain.NeedsTag;
import com.server.ggini.domain.restaurant.domain.PriceTag;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.domain.RestaurantImage;
import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.domain.review.dto.response.ReviewGetDetailsResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record RestaurantGetDetailsResponse(
        Long id,
        String name,
        String address,
        String roadAddress,
        String menuCategory,
        List<NeedsTagGetResponse> needsTags,
        List<PriceTagGetResponse> priceTags,
        List<String> restaurantImageUrls,
        ReviewGetDetailsResponse myReview,
        List<ReviewGetDetailsResponse> reviews
) {

    public static RestaurantGetDetailsResponse of(Restaurant restaurant, Review myReview, List<Review> reviews, List<NeedsTag> needsTags, List<PriceTag> priceTags) {
        return RestaurantGetDetailsResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress().getAddress())
                .roadAddress(restaurant.getAddress().getRoadAddress())
                .menuCategory(restaurant.getMenuCategory().getName())
                .needsTags(needsTags.stream().map(NeedsTagGetResponse::from).toList())
                .priceTags(priceTags.stream().map(PriceTagGetResponse::from).toList())
                .restaurantImageUrls(restaurant.getRestaurantImage().stream().map(RestaurantImage::getUrl).toList())
                .myReview(ReviewGetDetailsResponse.from(myReview))
                .reviews(reviews.stream().map(ReviewGetDetailsResponse::from).toList())
                .build();
    }
}
