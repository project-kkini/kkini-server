package com.server.ggini.domain.restaurant.service;

import com.server.ggini.domain.member.domain.CompanyLocation;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.dto.response.RestaurantsNearbyGetResponse;
import com.server.ggini.domain.restaurant.dto.response.SeoulConfirmRestaurantGetResponse;
import com.server.ggini.domain.restaurant.repository.SeoulConfirmRestaurantRepository;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SeoulConfirmRestaurantService {
    private final SeoulConfirmRestaurantRepository seoulConfirmRestaurantRepository;

    public RestaurantsNearbyGetResponse findRestaurantsNearby(Member member, String keyword, int radius, int page) {
        CompanyLocation companyLocation = member.getCompanyLocation();
        if(companyLocation == null) {
            throw new NotFoundException(ErrorCode.COMPANY_LOCATION_NOT_REGISTERED);
        }
        double latitude = companyLocation.getCoordinate().getLatitude();
        double longitude = companyLocation.getCoordinate().getLongitude();

        String searchKeyword = (keyword == null) ? "" : keyword;

        // 1. 반경 내 음식점 조회
        Pageable pageable = PageRequest.of(page, 20);
        Slice<SeoulConfirmRestaurantGetResponse> restaurantsNearby = seoulConfirmRestaurantRepository.findRestaurantsByNameAndLocation(
            searchKeyword, latitude , longitude, radius, pageable);

        // 반경 내에 음식점이 있는 경우
        if (!restaurantsNearby.isEmpty()) {
            return new RestaurantsNearbyGetResponse(
                RestaurantsNearbyGetResponse.SearchNearbyResultType.FOUND_WITHIN_RADIUS,
                restaurantsNearby.getContent(),
                restaurantsNearby.getNumber(),
                restaurantsNearby.hasNext()
            );
        }

        // 2. 반경 외부에 음식점의 존재 여부만 간단히 확인
        boolean existsAny = seoulConfirmRestaurantRepository.existsRestaurantOutsideRadius(searchKeyword, latitude, longitude, radius) == 1;

        // 검색어와 일치하는 음식점은 있지만 반경 내에 없는 경우
        if (existsAny) {
            return new RestaurantsNearbyGetResponse(
                RestaurantsNearbyGetResponse.SearchNearbyResultType.FOUND_OUTSIDE_RADIUS,
                Collections.emptyList(),
                null,
                null
            );
        }

        // 3. 검색어와 일치하는 음식점이 없는 경우
        return new RestaurantsNearbyGetResponse(
            RestaurantsNearbyGetResponse.SearchNearbyResultType.NOT_FOUND,
            Collections.emptyList(),
            null,
            null
        );
    }
}
