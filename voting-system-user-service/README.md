# 🧑‍💼 Voting System - User Service

Microsserviço responsável por autenticação, cadastro de usuários e emissão de tokens JWT no sistema distribuído de votação.

## 📌 Função na Arquitetura

* Gestão de identidade e sessões
* Gera e valida tokens JWT para autenticação de usuários
* Expõe endpoints para cadastro, login e consulta de dados do usuário

## 📁 Estrutura de Pacotes

```
br/com/voting_system_user_service
├── config
│   ├── CorsConfig.java
│   └── SecurityConfig.java
├── controller
│   ├── AuthController.java
│   └── UserController.java
├── dto
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   └── UserDTO.java
├── entity
│   └── User.java
├── enums
│   └── Role.java
├── repository
│   └── UserRepository.java
├── security
│   ├── JwtAuthenticationFilter.java
│   └── JwtUtil.java
├── service
│   ├── AuthService.java
│   └── UserService.java
├── VotingSystemUserServiceApplication.java
└── resources
```

## 🔐 Funcionalidades

* Cadastro de usuário (e-mail, senha, nome, papel)
* Login com validação de credenciais e retorno de JWT
* Proteção de endpoints com Spring Security e filtros JWT
* Atribuição de roles (ex: `USER`, `ADMIN`)

## 🔧 Tecnologias Utilizadas

* Java 17
* Spring Boot 3.x
* Spring Security
* JWT (via `io.jsonwebtoken`)
* Spring Data JPA
* PostgreSQL

## 🧪 Endpoints

| Método | URI              | Descrição                    |
| ------ | ---------------- | ---------------------------- |
| POST   | `/auth/register` | Cadastro de novo usuário     |
| POST   | `/auth/login`    | Login e retorno de JWT       |
| GET    | `/users/me`      | Dados do usuário autenticado |

## 🚀 Executando Localmente

### Requisitos

* PostgreSQL rodando (padrão: porta 5432)
* JDK 17
* Docker (opcional)

### Passos

```bash
# build
./mvnw clean install

# executar
java -jar target/voting-system-user-service.jar
```

## 🔄 Integração JWT

* Após login, o token JWT deve ser usado como:

```
Authorization: Bearer <jwt-token>
```

* Filtros de autenticação interceptam e validam esse token automaticamente

## 📚 Documentação Swagger

Acesse `http://localhost:8081/swagger-ui/index.html`

## 📈 Exemplo de JSON

### Registro

```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123",
  "fullName": "João da Silva"
}
```

### Login

```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

## 📄 Licença

[MIT License](../LICENSE)
