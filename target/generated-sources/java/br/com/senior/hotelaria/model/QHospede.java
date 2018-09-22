package br.com.senior.hotelaria.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHospede is a Querydsl query type for Hospede
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHospede extends EntityPathBase<Hospede> {

    private static final long serialVersionUID = 1825114329L;

    public static final QHospede hospede = new QHospede("hospede");

    public final StringPath documento = createString("documento");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nome = createString("nome");

    public final StringPath telefone = createString("telefone");

    public QHospede(String variable) {
        super(Hospede.class, forVariable(variable));
    }

    public QHospede(Path<? extends Hospede> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHospede(PathMetadata metadata) {
        super(Hospede.class, metadata);
    }

}

