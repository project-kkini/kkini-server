package com.server.ggini.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewImage is a Querydsl query type for ReviewImage
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReviewImage extends BeanPath<ReviewImage> {

    private static final long serialVersionUID = 1555170637L;

    public static final QReviewImage reviewImage = new QReviewImage("reviewImage");

    public final StringPath url = createString("url");

    public QReviewImage(String variable) {
        super(ReviewImage.class, forVariable(variable));
    }

    public QReviewImage(Path<? extends ReviewImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewImage(PathMetadata metadata) {
        super(ReviewImage.class, metadata);
    }

}

