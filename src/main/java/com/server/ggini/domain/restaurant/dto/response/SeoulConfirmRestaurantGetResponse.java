package com.server.ggini.domain.restaurant.dto.response;

public record SeoulConfirmRestaurantGetResponse(
        Long id,
        String name,
        String roadAddress,
        Long distance
) {
}
