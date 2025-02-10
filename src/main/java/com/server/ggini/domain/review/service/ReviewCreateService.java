package com.server.ggini.domain.review.service;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.repository.NeedsTagRepository;
import com.server.ggini.domain.restaurant.repository.PriceTagsRepository;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.review.dto.request.ReviewPostRequest;
import com.server.ggini.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewCreateService {

    private final NeedsTagRepository needsTagRepository;
    private final PriceTagsRepository priceTagRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long createReview(Long restaurantId, ReviewPostRequest request, Member member) {
        needsTagRepository.existsByIdsElseThrow(request.needsTagIds());
        priceTagRepository.existsByIdsElseThrow(request.priceTagIds());

        Restaurant restaurant = restaurantRepository.findByIdElseThrow(restaurantId);
        reviewRepository.existsByReviewerIdAndRestaurantIdElseThrow(member.getId(), restaurantId);
        reviewRepository.save(request.toEntity(member, restaurant, false));

        return restaurant.getId();
    }
}
