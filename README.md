# Product Service

Microserviço responsável pela gestão de produtos.

## O que ele faz?

- Gerencia catálogo de produtos.
- Fornece endpoints REST para consulta e manutenção.
- Persiste dados no PostgreSQL.
- Registrado no Eureka para descoberta.

## Tecnologias usadas

- Spring Boot
- Spring Data JPA com PostgreSQL
- Eureka Client
- Docker
- Swagger

## Endpoints principais

- GET /api/products
- POST /api/products
- PUT /api/products/{id}
- DELETE /api/products/{id}


