package com.server.ggini.domain.review.domain;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.global.common.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Content content;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "review_image", joinColumns = @JoinColumn(name = "review_id"))
    @OrderColumn(name = "sequence")
    private List<ReviewImage> imageUrls;

    @Embedded
    private Reviewer reviewer;
    private Long restaurantId;
    private boolean isFirstReview;

    @ElementCollection
    @CollectionTable(name = "review_needs_tag", joinColumns = @JoinColumn(name = "review_id"))
    private List<Long> needsTagIds;

    @ElementCollection
    @CollectionTable(name = "review_price_tag", joinColumns = @JoinColumn(name = "review_id"))
    private List<Long> priceTagIds;

    @Builder
    public Review(String content, List<String> imageUrls, Member reviewer, Long restaurantId, List<Long> needsTagIds,
                  List<Long> priceTagIds, boolean isFirstReview) {
        List<ReviewImage> images = imageUrls.stream()
                .map(ReviewImage::new)
                .toList();

        this.content = new Content(content);
        this.imageUrls = images;
        this.reviewer = Reviewer.from(reviewer);
        this.restaurantId = restaurantId;
        this.needsTagIds = needsTagIds;
        this.priceTagIds = priceTagIds;
        this.isFirstReview = isFirstReview;
    }


    public String getContent() {
        return content.getContent();
    }
}
