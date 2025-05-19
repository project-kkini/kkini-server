package com.server.ggini.domain.restaurant.dto.response;

public record SeoulConfirmRestaurantGetResponse(
        Long id,
        String name,
        String roadAddress,
		Double distance // Long -> argument type mismatch
) {
}
