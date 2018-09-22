# Hotelaria 0.0.1
App de hotelaria RESTful


## Objetivo
#### Desenvolver uma aplicação (somente o backend) que possibilite realizar o cadastro de hóspedes e o check-in.`

Queremos ver como você resolve problemas no seu dia-a-dia. Não há necessidade de
desenvolver o frontend da aplicação, vamos utilizar o Postman para testar sua
aplicação.

### Requisitos funcionais
- Um CRUDL para o cadastro de hóspedes;
- No check in deve ser possível buscar hóspedes cadastrados pelo nome,
documento ou telefone;
- Consultar hóspedes que já realizaram o check in e não estão mais no hotel;
- Consultar hóspedes que ainda estão no hotel;
- As consultas devem apresentar o valor (Valor total e o valor da última
hospedagem) já gasto pelo hóspede no hotel;
- *JSON exemplo do hóspede:*
`
{
“nome”: “Fulano da SIlva”,
“documento”: “123456”,
“telefone”: “9925-2211”
}`

- *JSON exemplo do check in:*
`
{
“hospede”: {...},
“dataEntrada”: “2018-03-14T08:00:00”,
“dataSaida”: “2018-03-16T10:17:00”,
“adicionalVeiculo”: false/true
}
`
* data no padrão ISO-8601;

### Regras de negócio
- Uma diária no hotel de segunda à sexta custa R$120,00;

- Uma diária no hotel em finais de semana custa R$150,00;

- Caso a pessoa precise de uma vaga na garagem do hotel há um acréscimo
diário, sendo R$15,00 de segunda à sexta e R$20,00 nos finais de semana;

- Caso o horário da saída seja após às 16:30h deve ser cobrada uma diária extra;


## Get Started

Para executar o projeto, é necessário dar um clone no repositório e importar como um projeto maven.

- Se faz necessário a configuração de um banco de dados Postgres 9.x ou maior 

- Dentro de src/main/resources, no arquivo application.properties, colocar o caminho para o banco de dados na propriedade 'spring.datasource.url'

- O servidor tomcat iniciará na porta 8086 conforme o parâmetro 'server.port', que pode também ser alterado

### Caminhos

#### Hóspede
* POST - http://localhost:{server.port}/hospede/ - Cria um novo hóspede

* GET - http://localhost:{server.port}/hospede/{id} - Recupera um hóspede pelo seu ID

* PUT - http://localhost:{server.port}/hospede/{id} - Altera um hóspede pelo seu ID

* DEL - http://localhost:{server.port}/hospede/{id} - Deleta um hóspede pelo seu ID

* GET - http://localhost:{server.port}/hospede/ - Lista todos os hóspedes cadastrados

* POST - http://localhost:{server.port}/hospede/buscarHospedes - Busca hóspedes por um dado nome, telefone ou documento.

#### Estadia
* POST - http://localhost:{server.port}/estadia/checkin - Realiza o checkin de determinado hóspede

* POST - http://localhost:{server.port}/estadia/checkout - Realiza o checkout de determinado hóspede

* GET - http://localhost:{server.port}/estadia/{id} - Recupera uma estadia pelo seu ID

* GET - http://localhost:{server.port}/estadia/inativos - Busca todos os hóspedes que não estão mais no hotel com o valor total e o valor da última
hospedagem

* GET - http://localhost:{server.port}/estadia/ativos - Busca todos os hóspedes que ainda estão no hotel com o valor total e o valor da última
hospedagem


