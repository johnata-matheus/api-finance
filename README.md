# Api-Finance

![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![sring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![mysql](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

Este projeto se trata de uma API de finanças. A mesma foi construída utilizando as tecnologias: Java, Java Spring, Spring MVC, Spring Data JPA, Spring Security, JWT, MYSQL e Docker.

## Diagrama ER

![image](https://github.com/johnata-matheus/api-finance/assets/105123252/85275a75-b147-4949-8078-577e84612c79)

## Requisitos

Para rodar esse projeto você precisará do Docker.

## Instalação

1. Clonar o repositório:

```
git clone https://github.com/johnata-matheus/api-finance.git
```

2. Na pasta do projeto, starte o projeto:
   
```
docker-compose up -d
```

## API Endpoints

### Auth

```
POST /auth/register - Cria um novo usuário
POST /auth/login - Faz o login do usuário existente
POST /auth/validate-token - Valida um token
```
### Expense

```
GET /expense - Lista todas as despesas
GET /expense/id - Lista uma despesa pelo id
POST /expense - Cria uma nova despesa
PUT /expense/id - Altera uma despesa pelo seu id
DELETE /expense/id - Deleta uma despesa pelo seu id
```

### Revenue

```
GET /revenue - Lista todas as receitas
GET /revenue/id - Lista uma receita pelo id
POST /revenue - Cria uma nova receita
PUT /revenue/id - Altera uma receita pelo seu id
DELETE /revenue/id - Deleta uma receita pelo seu id
```

### User

```
GET /user - Lista todas os usuários
GET /user/id - Lista um usuário pelo id
PUT /user/id - Altera um usuário pelo seu id
DELETE /user/id - Deleta um usuário pelo seu id
```
