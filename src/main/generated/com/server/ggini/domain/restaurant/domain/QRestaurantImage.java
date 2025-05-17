package com.server.ggini.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestaurantImage is a Querydsl query type for RestaurantImage
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRestaurantImage extends BeanPath<RestaurantImage> {

    private static final long serialVersionUID = 549342979L;

    public static final QRestaurantImage restaurantImage = new QRestaurantImage("restaurantImage");

    public final StringPath url = createString("url");

    public QRestaurantImage(String variable) {
        super(RestaurantImage.class, forVariable(variable));
    }

    public QRestaurantImage(Path<? extends RestaurantImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurantImage(PathMetadata metadata) {
        super(RestaurantImage.class, metadata);
    }

}

