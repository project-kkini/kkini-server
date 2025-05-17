package com.server.ggini.domain.restaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = 471133496L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final com.server.ggini.global.common.QBaseEntity _super = new com.server.ggini.global.common.QBaseEntity(this);

    public final QAddress address;

    public final QCoordinate coordinate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCategory menuCategory;

    public final StringPath name = createString("name");

    public final ListPath<Long, NumberPath<Long>> needsTagIds = this.<Long, NumberPath<Long>>createList("needsTagIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<Long, NumberPath<Long>> priceTagIds = this.<Long, NumberPath<Long>>createList("priceTagIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    public final ListPath<RestaurantImage, QRestaurantImage> restaurantImage = this.<RestaurantImage, QRestaurantImage>createList("restaurantImage", RestaurantImage.class, QRestaurantImage.class, PathInits.DIRECT2);

    public final NumberPath<Long> seoulRestaurantId = createNumber("seoulRestaurantId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QRestaurant(String variable) {
        this(Restaurant.class, forVariable(variable), INITS);
    }

    public QRestaurant(Path<? extends Restaurant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRestaurant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRestaurant(PathMetadata metadata, PathInits inits) {
        this(Restaurant.class, metadata, inits);
    }

    public QRestaurant(Class<? extends Restaurant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.coordinate = inits.isInitialized("coordinate") ? new QCoordinate(forProperty("coordinate")) : null;
        this.menuCategory = inits.isInitialized("menuCategory") ? new QCategory(forProperty("menuCategory")) : null;
    }

}

