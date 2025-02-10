package com.server.ggini.domain.restaurant.dto.response;

import com.server.ggini.domain.restaurant.domain.PriceTag;

public record PriceTagGetResponse(
        Long id,
        String text
) {

    public static PriceTagGetResponse from(PriceTag priceTag) {
        return new PriceTagGetResponse(
                priceTag.getId(),
                priceTag.getText()
        );
    }
}
