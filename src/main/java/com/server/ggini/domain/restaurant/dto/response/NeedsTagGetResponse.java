package com.server.ggini.domain.restaurant.dto.response;

import com.server.ggini.domain.restaurant.domain.NeedsTag;

public record NeedsTagGetResponse(
        Long id,
        String text
) {

    public static NeedsTagGetResponse from(NeedsTag needsTag) {
        return new NeedsTagGetResponse(
                needsTag.getId(),
                needsTag.getText()
        );
    }
}
