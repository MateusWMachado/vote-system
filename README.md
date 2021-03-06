# Vote System

Quick guide to build, test, run, and use this application.

## Project clone and environment preparation

```shell script
$ https://github.com/MateusWMachado/vote-system.git
$ cd vote-system
$ docker-compose up -d
```

## How to build the application

To run the command "mvn install" it is necessary to have made the connection with the database before, otherwise the tests will fail, to know how to make the connection with the database, look at the section on how to run the application.

```shell script
$ mvn install
$ mvn spring-boot:run
```

## How to run the tests

```shell script
$ mvn test
```

## How to use the application

The application will be listening by default on HTTP port 8080.
After running the docker compose command, enter the url "localhost:16543" and login with the user "test@test.com.br" and password "root", then go to "Add New Server" and enter the following data:
- Host name/address: teste-postgres-compose
- Port: 5432
- Maintenance Database: postgres
- Username: postgres
- Password: Postgres2019! <br />

Then you will need to comment a line of code in application.properties and uncomment the rest about the database. <br />
Your code will look like this <br />
```shell script
 #spring.datasource.url=${JDBC_DATASOURCE-URL}
 
 spring.database.driverClassName=org.postgresql.Driver
 spring.datasource.url=jdbc:postgresql://localhost:15432/postgres
 spring.datasource.username=postgres
 spring.datasource.password=Postgres2019!
```

## Brief Endpoints documentation

### Schedule API
#### Create Schedule
```http request
POST http://localhost:8080/api/v1/schedule/
{
  "subject": "Teste new subject",
  "votes": []
}
``` 

#### Get the result of a schedule
```http request
GET http://localhost:8080/api/v1/schedule/{id}
``` 

### Voting Sessions API
#### Open Voting Session
```http request
POST http://localhost:8080/api/v1/vote-session/
{
  "duration": 3,
  "idSchedule": 1
}
``` 

### Votes API
#### Add New Vote
```http request
POST http://localhost:8080/api/v1/vote/
{
  "cpf": "123.123.123-00",
  "idSchedule": 1,
  "vote": "YES"
}
``` 

## Swagger documentation
This API is build with Swagger documentation generator. The complete API documentation can be retrieved in:
```http request
http://localhost:8080/swagger-ui.html
``` 

## Important considerations

- The application makes a post in kafka every time a person searches for the result of a schedule but this is not in heroku because it needs a paid plan so the kafka part is commented out in the code.
- The external cpf query service is down so every time a query is made it returns 404 NOT_FOUND and a associate can't vote, so for this not to impact the application I left this code snippet commented out.
- When creating a new schedule it is necessary to create an empty list ("votes": []).
- I used lombok in development so for the application to work it is recommended to use the IntelliJ IDE.

## About the implementation

- For API development: Java 11, Spring Framework and PostgreSQL database.
- For the conversion of the DTOs, I chose to use the MODELMAPPER lib, which abstracts this logic, making the code more readable.
- Lombok was used to speed up code writing and readability

## Heroku APP URL

https://challenge-vote-system-api.herokuapp.com/api/v1/

## Swagger

https://challenge-vote-system-api.herokuapp.com/swagger-ui.html#/


# Desafio T??cnico
## Objetivo
No cooperativismo, cada associado possui um voto e as decis??es s??o tomadas em assembleias, por vota????o. A partir disso, voc?? precisa criar uma solu????o back-end para gerenciar essas sess??es de vota????o. Essa solu????o deve ser executada na nuvem e promover as seguintes funcionalidades atrav??s de uma API REST:
- Cadastrar uma nova pauta;
- Abrir uma sess??o de vota????o em uma pauta (a sess??o de vota????o deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
- Receber votos dos associados em pautas (os votos s??o apenas 'Sim'/'N??o'. Cada associado ?? identificado por um id ??nico e pode votar apenas uma vez por pauta);
- Contabilizar os votos e dar o resultado da vota????o na pauta.

Para fins de exerc??cio, a seguran??a das interfaces pode ser abstra??da e qualquer chamada para as interfaces pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas ?? livre (desde que n??o infrinja direitos de uso).

?? importante que as pautas e os votos sejam persistidos e que n??o sejam perdidos com o restart da aplica????o.

### Tarefas b??nus
As tarefas b??nus n??o s??o obrigat??rias, mas nos permitem avaliar outros conhecimentos que voc?? possa ter.

A gente sempre sugere que o candidato pondere e apresente at?? onde consegue fazer, considerando o seu
n??vel de conhecimento e a qualidade da entrega.
#### Tarefa B??nus 1 - Integra????o com sistemas externos
Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode votar
- GET https://user-info.herokuapp.com/users/{cpf}
- Caso o CPF seja inv??lido, a API retornar?? o HTTP Status 404 (Not found). Voc?? pode usar geradores de CPF para gerar CPFs v??lidos;
- Caso o CPF seja v??lido, a API retornar?? se o usu??rio pode (ABLE_TO_VOTE) ou n??o pode (UNABLE_TO_VOTE) executar a opera????o
Exemplos de retorno do servi??o

#### Tarefa B??nus 2 - Mensageria e filas
Classifica????o da informa????o: Uso Interno
O resultado da vota????o precisa ser informado para o restante da plataforma, isso deve ser feito preferencialmente atrav??s de mensageria. Quando a sess??o de vota????o fechar, poste uma mensagem com o resultado da vota????o.

#### Tarefa B??nus 3 - Performance
Imagine que sua aplica????o possa ser usada em cen??rios que existam centenas de milhares de votos. Ela deve se comportar de maneira perform??tica nesses cen??rios;
- Testes de performance s??o uma boa maneira de garantir e observar como sua aplica????o se comporta.

#### Tarefa B??nus 4 - Versionamento da API
Como voc?? versionaria a API da sua aplica????o? Que estrat??gia usar?

### O que ser?? analisado
- Simplicidade no design da solu????o (evitar over engineering)
- Organiza????o do c??digo
- Arquitetura do projeto
- Boas pr??ticas de programa????o (manutenibilidade, legibilidade etc)
- Poss??veis bugs
- Tratamento de erros e exce????es
- Explica????o breve do porqu?? das escolhas tomadas durante o desenvolvimento da solu????o
- Uso de testes automatizados e ferramentas de qualidade
- Limpeza do c??digo
- Documenta????o do c??digo e da API
- Logs da aplica????o
- Mensagens e organiza????o dos commits

### Observa????es importantes
- N??o inicie o teste sem sanar todas as d??vidas
- Iremos executar a aplica????o para test??-la, cuide com qualquer depend??ncia externa e deixe claro caso haja instru????es especiais para execu????o do mesmo
- Teste bem sua solu????o, evite bugs
