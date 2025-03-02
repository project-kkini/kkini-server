package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.SeoulConfirmRestaurant;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeoulConfirmRestaurantRepository extends JpaRepository<SeoulConfirmRestaurant, Long> {

    default SeoulConfirmRestaurant findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.SEOUL_RESTAURANT_NOT_FOUND));
    }
}
