package com.server.ggini.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -146345810L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.server.ggini.global.common.QBaseEntity _super = new com.server.ggini.global.common.QBaseEntity(this);

    public final QContent content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ReviewImage, QReviewImage> imageUrls = this.<ReviewImage, QReviewImage>createList("imageUrls", ReviewImage.class, QReviewImage.class, PathInits.DIRECT2);

    public final BooleanPath isFirstReview = createBoolean("isFirstReview");

    public final ListPath<Long, NumberPath<Long>> needsTagIds = this.<Long, NumberPath<Long>>createList("needsTagIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Long, NumberPath<Long>> priceTagIds = this.<Long, NumberPath<Long>>createList("priceTagIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final QReviewer reviewer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new QContent(forProperty("content")) : null;
        this.reviewer = inits.isInitialized("reviewer") ? new QReviewer(forProperty("reviewer")) : null;
    }

}

