package br.com.senior.hotelaria.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstadia is a Querydsl query type for Estadia
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEstadia extends EntityPathBase<Estadia> {

    private static final long serialVersionUID = -722404264L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstadia estadia = new QEstadia("estadia");

    public final BooleanPath adicionalVeiculo = createBoolean("adicionalVeiculo");

    public final BooleanPath ativo = createBoolean("ativo");

    public final DateTimePath<java.time.LocalDateTime> dataEntrada = createDateTime("dataEntrada", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dataSaida = createDateTime("dataSaida", java.time.LocalDateTime.class);

    public final QHospede hospede;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> valorEstadia = createNumber("valorEstadia", Double.class);

    public QEstadia(String variable) {
        this(Estadia.class, forVariable(variable), INITS);
    }

    public QEstadia(Path<? extends Estadia> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstadia(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstadia(PathMetadata metadata, PathInits inits) {
        this(Estadia.class, metadata, inits);
    }

    public QEstadia(Class<? extends Estadia> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospede = inits.isInitialized("hospede") ? new QHospede(forProperty("hospede")) : null;
    }

}

