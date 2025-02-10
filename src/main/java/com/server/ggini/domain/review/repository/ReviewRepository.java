package com.server.ggini.domain.review.repository;

import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.InvalidValueException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.reviewer.id = :reviewerId AND r.restaurantId = :restaurantId")
    Optional<Review> findByReviewerIdAndRestaurantId(@Param("reviewerId") Long reviewerId, @Param("restaurantId") Long restaurantId);


    List<Review> findAllByRestaurantId(Long restaurantId);

    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.reviewer.id = :reviewerId AND r.restaurantId = :restaurantId")
    boolean existsByReviewerIdAndRestaurantId(@Param("reviewerId") Long reviewerId, @Param("restaurantId") Long restaurantId);

    default Review findByReviewerIdAndRestaurantIdElseNull(Long reviewerId, Long restaurantId) {
        return findByReviewerIdAndRestaurantId(reviewerId, restaurantId).orElse(null);
    }

    default void existsByReviewerIdAndRestaurantIdElseThrow(Long reviewerId, Long restaurantId) {
        if (existsByReviewerIdAndRestaurantId(reviewerId, restaurantId)) {
            throw new InvalidValueException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }
    }
}

