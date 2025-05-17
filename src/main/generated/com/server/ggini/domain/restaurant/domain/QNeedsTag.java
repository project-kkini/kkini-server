package com.server.ggini.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNeedsTag is a Querydsl query type for NeedsTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNeedsTag extends EntityPathBase<NeedsTag> {

    private static final long serialVersionUID = -838352776L;

    public static final QNeedsTag needsTag = new QNeedsTag("needsTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath text = createString("text");

    public QNeedsTag(String variable) {
        super(NeedsTag.class, forVariable(variable));
    }

    public QNeedsTag(Path<? extends NeedsTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNeedsTag(PathMetadata metadata) {
        super(NeedsTag.class, metadata);
    }

}

