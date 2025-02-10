package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.NotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findBySeoulRestaurantId(Long seoulRestaurantId);

    default Restaurant findBySeoulRestaurantIdOrThrow(Long seoulRestaurantId) {
        return findBySeoulRestaurantId(seoulRestaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESTAURANT_NOT_FOUND));
    }

    default Restaurant findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.RESTAURANT_NOT_FOUND));
    }
}
