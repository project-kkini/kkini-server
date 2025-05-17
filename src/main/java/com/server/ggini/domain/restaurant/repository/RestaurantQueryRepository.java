package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.Restaurant;
import java.util.List;

public interface RestaurantQueryRepository {
    List<Restaurant> searchRestaurants(
        List<Long> menuCategoryIds,
        List<Long> priceTagIds,
        List<Long> needsTagIds,
        String cursor,
        int size
    );
    
    Long countSearchResults(
        List<Long> menuCategoryIds,
        List<Long> priceTagIds,
        List<Long> needsTagIds
    );
}
