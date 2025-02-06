package com.server.ggini.domain.review.dto.response;

import com.server.ggini.domain.review.domain.ReviewImage;
import java.time.LocalDate;
import java.util.List;

public record ReviewGetDetailsResponse(
        Long id,
        String content,
        List<String> imageUrl,
        LocalDate createdAt
) {
}
