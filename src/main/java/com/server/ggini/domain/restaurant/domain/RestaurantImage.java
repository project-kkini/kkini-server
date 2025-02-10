package com.server.ggini.domain.restaurant.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantImage {

    private String url;

    public RestaurantImage(String url) {
        this.url = url;
    }
}
