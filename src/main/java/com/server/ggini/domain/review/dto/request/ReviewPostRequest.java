package com.server.ggini.domain.review.dto.request;

import java.util.List;

public record ReviewPostRequest(
        String content,
        List<Long> needsTagIds,
        List<Long> priceTagIds,
        List<String> reviewImages
) {
}
