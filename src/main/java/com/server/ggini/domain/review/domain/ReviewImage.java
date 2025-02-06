package com.server.ggini.domain.review.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {

    private String url;
    private int sequence;

    public ReviewImage(String url, int sequence) {
        this.url = url;
        this.sequence = sequence;
    }
}
