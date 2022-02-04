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
Comment the line <br />
```shell script
 spring.datasource.url=${JDBC_DATASOURCE-URL}
```
<br />
Your code will look like this
<br />
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
POST http://localhost:8080/api/v1/voting-session/
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


# Desafio Técnico
## Objetivo
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação. Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:
- Cadastrar uma nova pauta;
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta);
- Contabilizar os votos e dar o resultado da votação na pauta.

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que não infrinja direitos de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

### Tarefas bônus
As tarefas bônus não são obrigatórias, mas nos permitem avaliar outros conhecimentos que você possa ter.

A gente sempre sugere que o candidato pondere e apresente até onde consegue fazer, considerando o seu
nível de conhecimento e a qualidade da entrega.
#### Tarefa Bônus 1 - Integração com sistemas externos
Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode votar
- GET https://user-info.herokuapp.com/users/{cpf}
- Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos;
- Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação
Exemplos de retorno do serviço

#### Tarefa Bônus 2 - Mensageria e filas
Classificação da informação: Uso Interno
O resultado da votação precisa ser informado para o restante da plataforma, isso deve ser feito preferencialmente através de mensageria. Quando a sessão de votação fechar, poste uma mensagem com o resultado da votação.

#### Tarefa Bônus 3 - Performance
Imagine que sua aplicação possa ser usada em cenários que existam centenas de milhares de votos. Ela deve se comportar de maneira performática nesses cenários;
- Testes de performance são uma boa maneira de garantir e observar como sua aplicação se comporta.

#### Tarefa Bônus 4 - Versionamento da API
Como você versionaria a API da sua aplicação? Que estratégia usar?

### O que será analisado
- Simplicidade no design da solução (evitar over engineering)
- Organização do código
- Arquitetura do projeto
- Boas práticas de programação (manutenibilidade, legibilidade etc)
- Possíveis bugs
- Tratamento de erros e exceções
- Explicação breve do porquê das escolhas tomadas durante o desenvolvimento da solução
- Uso de testes automatizados e ferramentas de qualidade
- Limpeza do código
- Documentação do código e da API
- Logs da aplicação
- Mensagens e organização dos commits

### Observações importantes
- Não inicie o teste sem sanar todas as dúvidas
- Iremos executar a aplicação para testá-la, cuide com qualquer dependência externa e deixe claro caso haja instruções especiais para execução do mesmo
- Teste bem sua solução, evite bugs
