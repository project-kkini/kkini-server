package com.server.ggini.domain.review.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "review_image", joinColumns = @JoinColumn(name = "review_id"))
    private List<ReviewImage> imageUrls;

    private Long memberId;
    private Long restaurantId;
    @ElementCollection
    @CollectionTable(name = "review_needs_tag", joinColumns = @JoinColumn(name = "review_id"))
    private List<Long> needsTagIds;

    @ElementCollection
    @CollectionTable(name = "review_price_tag", joinColumns = @JoinColumn(name = "review_id"))
    private List<Long> priceTagIds;

    @Builder
    public Review(String content, List<ReviewImage> imageUrls, Long memberId, Long restaurantId, List<Long> needsTagIds,
                  List<Long> priceTagIds) {
        this.content = content;
        this.imageUrls = imageUrls;
        this.memberId = memberId;
        this.restaurantId = restaurantId;
        this.needsTagIds = needsTagIds;
        this.priceTagIds = priceTagIds;
    }
}
