create table estadia (
  id integer primary key, 
  hospede_id integer not null,
  FOREIGN KEY (hospede_id) REFERENCES hospede(id), 
  data_entrada timestamp not null, 
  data_saida timestamp, 
  adicional_veiculo boolean default false, 
  ativo boolean default true, 
  valor_estadia numeric
);

create sequence estadia_seq owned by estadia.id;
