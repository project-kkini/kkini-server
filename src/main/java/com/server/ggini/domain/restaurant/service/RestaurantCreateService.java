package com.server.ggini.domain.restaurant.service;

import com.server.ggini.domain.restaurant.domain.Category;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.domain.SeoulConfirmRestaurant;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.restaurant.repository.SeoulConfirmRestaurantRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantCreateService {

    private final SeoulConfirmRestaurantRepository seoulConfirmRestaurantRepository;
    private final CategoryService categoryService;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    //TODO: 여기에 Lock 걸어야댐
    public Restaurant createRestaurant(Long seoulRestaurantId, List<Long> needsTagIds, List<Long> priceTagIds) {
        SeoulConfirmRestaurant seoulConfirmRestaurant = seoulConfirmRestaurantRepository.findByIdElseThrow(seoulRestaurantId);
        Category category = categoryService.getCategoryByName(seoulConfirmRestaurant.getCategory());

        return restaurantRepository.save(Restaurant.of(seoulConfirmRestaurant, category, needsTagIds, priceTagIds));
    }
}
