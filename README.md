# Inscrição e Criação de Eventos - API

Este projeto é uma API desenvolvida com Spring Boot no Evento da @rocketseat NLW - Connect para gestão de eventos, permitindo a criação e inscrição de participantes.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot** (Spring Web, Spring Data JPA, Spring Validation)
- **Lombok**
- **Banco de Dados MySQL**
- **Insomnia** (para testes de API)

## Configuração do Projeto

### 1. Clonar o Repositório
```sh
git clone https://github.com/MoraesFS-N/nlw-connect-java
cd nlw-connect-java
```

### 2. Configurar o Banco de Dados
No arquivo `application.properties` ou `application.yml`, configure o banco de dados:

Para MySQL:
```properties
spring.application.name=events
spring.jpa.show-sql=true
spring.datasource.url=jdbc:mysql://localhost:3306/db_events
spring.datasource.username=seu_username
spring.datasource.password=sua_senha
spring.jpa.database=mysql
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Executar a API
```sh
./mvnw spring-boot:run
```

## Estrutura do Projeto
```
/src/main/java/com/nlw/events
  ├── controller       # Camada de controle (endpoints)
  ├── dto              # Data Transfer Objects (DTOs - Records)
  ├── Model            # Entidades do banco de dados
  ├── repository       # Interfaces de persistência
  ├── exception        # Exceptions tratadas
  ├── service          # Regras de negócio
```

## Endpoints Disponíveis (Exemplos)

### Criar Evento
**POST /events**
```json
{
	"title": "Multilog Service",
	"location": "Online",
	"price": 0.0,
	"startDate": "2025-03-30",
	"endDate": "2025-04-15",
	"startTime": "08:00:00",
	"endTime": "22:00:00"
}
```

### Listar Eventos
**GET /events**

### Inscrição em Evento
**POST /subscription/{pretty-event}**
```json
{
  "name": "João Silva",
  "email": "joao@email.com"
}
```

## Testando com Insomnia
Instale o [Insomnia](https://insomnia.rest/download/)

## Licença
Este projeto está sob a licença MIT. Sinta-se à vontade para usá-lo e modificá-lo.

---

Feito com ❤️ por [Moraes Neto](https://github.com/MoraesFS-N)

