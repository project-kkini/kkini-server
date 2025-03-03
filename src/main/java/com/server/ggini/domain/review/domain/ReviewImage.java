package com.server.ggini.domain.review.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {

    private String url;

    public ReviewImage(String url) {
        this.url = url;
    }
}
