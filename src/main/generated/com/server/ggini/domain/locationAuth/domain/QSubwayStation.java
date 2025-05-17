package com.server.ggini.domain.locationAuth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubwayStation is a Querydsl query type for SubwayStation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubwayStation extends EntityPathBase<SubwayStation> {

    private static final long serialVersionUID = -1402515862L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubwayStation subwayStation = new QSubwayStation("subwayStation");

    public final com.server.ggini.global.common.QCoordinate coordinate;

    public final StringPath district = createString("district");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath line = createString("line");

    public final StringPath name = createString("name");

    public QSubwayStation(String variable) {
        this(SubwayStation.class, forVariable(variable), INITS);
    }

    public QSubwayStation(Path<? extends SubwayStation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubwayStation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubwayStation(PathMetadata metadata, PathInits inits) {
        this(SubwayStation.class, metadata, inits);
    }

    public QSubwayStation(Class<? extends SubwayStation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coordinate = inits.isInitialized("coordinate") ? new com.server.ggini.global.common.QCoordinate(forProperty("coordinate")) : null;
    }

}

