package com.server.ggini.domain.review.service;

import com.amazonaws.HttpMethod;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.global.util.s3.S3Util;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewImageUploadService {

    private static final String RESTAURANT_IMAGE_PREFIX = "restaurant/";
    private final S3Util s3Util;
    private final RestaurantRepository restaurantRepository;

    public String generateReviewImagePresignedUrl(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdElseThrow(restaurantId);
        String fileName = generateReviewImageFileName(restaurant);
        return s3Util.getS3PresignedUrl(fileName, HttpMethod.PUT);
    }

    private String generateReviewImageFileName(Restaurant restaurant) {
        String uuid = UUID.randomUUID().toString();
        return String.format("%s/%s/%s.jpg",
                RESTAURANT_IMAGE_PREFIX,
                restaurant.getId() + " - " +restaurant.getName(),
                uuid
        );
    }

    public String getImageUrl(String fileName) {
        return s3Util.getS3ObjectUrl(fileName);
    }
} 