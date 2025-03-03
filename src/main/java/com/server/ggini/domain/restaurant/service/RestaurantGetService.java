package com.server.ggini.domain.restaurant.service;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.NeedsTag;
import com.server.ggini.domain.restaurant.domain.PriceTag;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.dto.response.RestaurantGetDetailsResponse;
import com.server.ggini.domain.restaurant.repository.NeedsTagRepository;
import com.server.ggini.domain.restaurant.repository.PriceTagsRepository;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantGetService {

    private final RestaurantRepository restaurantRepository;
    private final NeedsTagRepository needsTagRepository;
    private final PriceTagsRepository priceTagsRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantGetDetailsResponse getRestaurant(Long restaurantId, Member member) {
        //TODO: 한번에 Fetch Join으로 가져오게 리팩토링 필요
        Restaurant restaurant = restaurantRepository.findByIdElseThrow(restaurantId);
        List<NeedsTag> needsTags = needsTagRepository.findAllByIdIn(restaurant.getNeedsTagIds());
        List<PriceTag> priceTags = priceTagsRepository.findAllByIdIn(restaurant.getPriceTagIds());

        Review myReviewOrNull = reviewRepository.findByReviewerIdAndRestaurantIdElseNull(member.getId(), restaurantId);
        List<Review> allReviews = reviewRepository.findAllByRestaurantId(restaurantId);

        return RestaurantGetDetailsResponse.of(restaurant, myReviewOrNull, allReviews, needsTags, priceTags);
    }
}
