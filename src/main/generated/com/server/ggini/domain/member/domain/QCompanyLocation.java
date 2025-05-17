package com.server.ggini.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyLocation is a Querydsl query type for CompanyLocation
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCompanyLocation extends BeanPath<CompanyLocation> {

    private static final long serialVersionUID = -243696326L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyLocation companyLocation = new QCompanyLocation("companyLocation");

    public final com.server.ggini.global.common.QCoordinate coordinate;

    public final NumberPath<Long> nearestStationId = createNumber("nearestStationId", Long.class);

    public QCompanyLocation(String variable) {
        this(CompanyLocation.class, forVariable(variable), INITS);
    }

    public QCompanyLocation(Path<? extends CompanyLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyLocation(PathMetadata metadata, PathInits inits) {
        this(CompanyLocation.class, metadata, inits);
    }

    public QCompanyLocation(Class<? extends CompanyLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coordinate = inits.isInitialized("coordinate") ? new com.server.ggini.global.common.QCoordinate(forProperty("coordinate")) : null;
    }

}

