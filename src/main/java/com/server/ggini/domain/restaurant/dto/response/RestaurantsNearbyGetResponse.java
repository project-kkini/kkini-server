package com.server.ggini.domain.restaurant.dto.response;

import java.util.List;

public record RestaurantsNearbyGetResponse(
	SearchNearbyResultType status,
	List<SeoulConfirmRestaurantGetResponse> restaurants
) {
	// 상태 타입 열거형
	public enum SearchNearbyResultType {
		FOUND_WITHIN_RADIUS,    // 700m 이내에 음식점이 있는 경우
		FOUND_OUTSIDE_RADIUS,  // 음식점은 있지만 700m 이내에 없는 경우
		NOT_FOUND               // 검색어와 일치하는 음식점이 없는 경우
	}
}
