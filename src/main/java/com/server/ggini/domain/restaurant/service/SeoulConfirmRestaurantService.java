package com.server.ggini.domain.restaurant.service;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.dto.response.SeoulConfirmRestaurantGetResponse;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.restaurant.repository.SeoulConfirmRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeoulConfirmRestaurantService {
    private final SeoulConfirmRestaurantRepository seoulConfirmRestaurantRepository;

    public List<SeoulConfirmRestaurantGetResponse> findRestaurantsNearby(Member member, String keyword, Double latitude, Double longitude, int radius) {
        String searchKeyword = (keyword == null) ? "" : keyword;
        return seoulConfirmRestaurantRepository.findRestaurantsByNameAndLocation(searchKeyword, latitude, longitude, radius);
    }
}
