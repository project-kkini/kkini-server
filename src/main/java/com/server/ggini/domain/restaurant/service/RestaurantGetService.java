package com.server.ggini.domain.restaurant.service;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.NeedsTag;
import com.server.ggini.domain.restaurant.domain.PriceTag;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.domain.RestaurantImage;
import com.server.ggini.domain.restaurant.dto.response.PriceTagGetResponse;
import com.server.ggini.domain.restaurant.dto.response.RestaurantGetDetailsResponse;
import com.server.ggini.domain.restaurant.dto.response.RestaurantGetSummaryResponse;
import com.server.ggini.domain.restaurant.repository.NeedsTagRepository;
import com.server.ggini.domain.restaurant.repository.PriceTagsRepository;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.restaurant.repository.RestaurantQueryRepository;
import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.domain.review.dto.response.ReviewGetDetailsResponse;
import com.server.ggini.domain.review.repository.ReviewRepository;
import com.server.ggini.domain.restaurant.dto.response.CursorBasedListResponse;
import java.util.List;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantGetService {

    private final RestaurantRepository restaurantRepository;
    private final NeedsTagRepository needsTagRepository;
    private final PriceTagsRepository priceTagsRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;

    public RestaurantGetDetailsResponse getRestaurant(Long restaurantId, Member member) {
        //TODO: 한번에 Fetch Join으로 가져오게 리팩토링 필요
        Restaurant restaurant = restaurantRepository.findByIdElseThrow(restaurantId);
        List<NeedsTag> needsTags = needsTagRepository.findAllByIdIn(restaurant.getNeedsTagIds());
        List<PriceTag> priceTags = priceTagsRepository.findAllByIdIn(restaurant.getPriceTagIds());

        Review myReviewOrNull = reviewRepository.findByReviewerIdAndRestaurantIdElseNull(member.getId(), restaurantId);
        List<Review> allReviews = reviewRepository.findAllByRestaurantId(restaurantId);

        return RestaurantGetDetailsResponse.of(restaurant, myReviewOrNull, allReviews, needsTags, priceTags);
    }

    public CursorBasedListResponse<RestaurantGetSummaryResponse> searchRestaurants(
        List<Long> menuCategoryIds,
        List<Long> priceTagIds,
        List<Long> needsTagIds,
        String cursor,
        int size
    ) {
        List<Restaurant> restaurants = restaurantQueryRepository.searchRestaurants(
            menuCategoryIds,
            priceTagIds,
            needsTagIds,
            cursor,
            size + 1  // 다음 페이지 존재 여부 확인을 위해 1개 더 조회
        );
        
        boolean hasNext = restaurants.size() > size;
        if (hasNext) {
            restaurants = restaurants.subList(0, size);
        }
        
        List<RestaurantGetSummaryResponse> responses = restaurants.stream()
            .map(restaurant -> {
                List<PriceTag> priceTags = priceTagsRepository.findAllByIdIn(restaurant.getPriceTagIds());
                List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurant.getId());
                
                return new RestaurantGetSummaryResponse(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getAddress().getRoadAddress(),
                    restaurant.getMenuCategory().getName(),
                    (long) reviews.size(),
                    priceTags.stream()
                        .map(PriceTagGetResponse::from)
                        .toList(),
                    restaurant.getRestaurantImage().stream()
                        .map(RestaurantImage::getUrl)
                        .toList(),
                    reviews.stream()
                        .map(ReviewGetDetailsResponse::from)
                        .toList()
                );
            })
            .toList();
        
        String nextCursor = hasNext ? CursorBasedListResponse.generateCursor(restaurants.get(restaurants.size() - 1).getId()) : null;
        
        return CursorBasedListResponse.of(responses, nextCursor);
    }

    public Long countSearchResults(
        List<Long> menuCategoryIds,
        List<Long> priceTagIds,
        List<Long> needsTagIds
    ) {
        return restaurantQueryRepository.countSearchResults(
            menuCategoryIds,
            priceTagIds,
            needsTagIds
        );
    }
}
