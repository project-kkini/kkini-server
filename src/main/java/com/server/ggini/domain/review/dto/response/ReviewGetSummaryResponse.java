package com.server.ggini.domain.review.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ReviewGetSummaryResponse(
        Long id,
        String content,
        LocalDate createdAt
) {
}
