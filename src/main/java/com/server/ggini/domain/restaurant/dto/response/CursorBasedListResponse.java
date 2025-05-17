package com.server.ggini.domain.restaurant.dto.response;

import java.util.Base64;
import java.util.List;

public record CursorBasedListResponse<T>(
    List<T> items,
    String nextCursor,
    boolean hasNext
) {
    public static <T> CursorBasedListResponse<T> of(List<T> items, String nextCursor) {
        return new CursorBasedListResponse<>(
            items,
            nextCursor,
            nextCursor != null
        );
    }
    
    public static String generateCursor(Long id) {
        return Base64.getEncoder().encodeToString(id.toString().getBytes());
    }
} 