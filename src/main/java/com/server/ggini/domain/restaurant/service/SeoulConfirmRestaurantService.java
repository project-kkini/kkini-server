package com.server.ggini.domain.restaurant.service;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.dto.response.RestaurantsNearbyGetResponse;
import com.server.ggini.domain.restaurant.dto.response.SeoulConfirmRestaurantGetResponse;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.restaurant.repository.SeoulConfirmRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeoulConfirmRestaurantService {
    private final SeoulConfirmRestaurantRepository seoulConfirmRestaurantRepository;

    public RestaurantsNearbyGetResponse findRestaurantsNearby(Member member, String keyword, Double latitude, Double longitude, int radius) {
        String searchKeyword = (keyword == null) ? "" : keyword;

        // 1. 반경 내 음식점 조회
        List<SeoulConfirmRestaurantGetResponse> restaurantsNearby = seoulConfirmRestaurantRepository.findRestaurantsByNameAndLocation(
            searchKeyword, latitude, longitude, radius);

        // 반경 내에 음식점이 있는 경우
        if (!restaurantsNearby.isEmpty()) {
            return new RestaurantsNearbyGetResponse(
                RestaurantsNearbyGetResponse.SearchNearbyResultType.FOUND_WITHIN_RADIUS,
                restaurantsNearby
            );
        }

        // 2. 반경 외부에 음식점의 존재 여부만 간단히 확인
        boolean existsAny = seoulConfirmRestaurantRepository.existsRestaurantOutsideRadius(searchKeyword, latitude, longitude, radius) == 1;

        // 검색어와 일치하는 음식점은 있지만 반경 내에 없는 경우
        if (existsAny) {
            return new RestaurantsNearbyGetResponse(
                RestaurantsNearbyGetResponse.SearchNearbyResultType.FOUND_OUTSIDE_RADIUS,
                Collections.emptyList()
            );
        }

        // 3. 검색어와 일치하는 음식점이 없는 경우
        return new RestaurantsNearbyGetResponse(
            RestaurantsNearbyGetResponse.SearchNearbyResultType.NOT_FOUND,
            Collections.emptyList()
        );
    }
}
