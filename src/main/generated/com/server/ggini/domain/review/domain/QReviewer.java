package com.server.ggini.domain.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewer is a Querydsl query type for Reviewer
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReviewer extends BeanPath<Reviewer> {

    private static final long serialVersionUID = 1095600603L;

    public static final QReviewer reviewer = new QReviewer("reviewer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public QReviewer(String variable) {
        super(Reviewer.class, forVariable(variable));
    }

    public QReviewer(Path<? extends Reviewer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewer(PathMetadata metadata) {
        super(Reviewer.class, metadata);
    }

}

