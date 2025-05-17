package com.server.ggini.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPriceTag is a Querydsl query type for PriceTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPriceTag extends EntityPathBase<PriceTag> {

    private static final long serialVersionUID = 1413089868L;

    public static final QPriceTag priceTag = new QPriceTag("priceTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath text = createString("text");

    public QPriceTag(String variable) {
        super(PriceTag.class, forVariable(variable));
    }

    public QPriceTag(Path<? extends PriceTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPriceTag(PathMetadata metadata) {
        super(PriceTag.class, metadata);
    }

}

