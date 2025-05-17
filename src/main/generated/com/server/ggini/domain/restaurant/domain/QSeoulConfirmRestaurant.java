package com.server.ggini.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeoulConfirmRestaurant is a Querydsl query type for SeoulConfirmRestaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeoulConfirmRestaurant extends EntityPathBase<SeoulConfirmRestaurant> {

    private static final long serialVersionUID = -83225020L;

    public static final QSeoulConfirmRestaurant seoulConfirmRestaurant = new QSeoulConfirmRestaurant("seoulConfirmRestaurant");

    public final StringPath address = createString("address");

    public final StringPath category = createString("category");

    public final NumberPath<Long> districtCode = createNumber("districtCode", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath managementNumber = createString("managementNumber");

    public final StringPath name = createString("name");

    public final StringPath roadAddress = createString("roadAddress");

    public final NumberPath<Integer> statusCode = createNumber("statusCode", Integer.class);

    public QSeoulConfirmRestaurant(String variable) {
        super(SeoulConfirmRestaurant.class, forVariable(variable));
    }

    public QSeoulConfirmRestaurant(Path<? extends SeoulConfirmRestaurant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeoulConfirmRestaurant(PathMetadata metadata) {
        super(SeoulConfirmRestaurant.class, metadata);
    }

}

