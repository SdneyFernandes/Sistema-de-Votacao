# 🧑‍💼 Voting System - User Service

Microsserviço dedicado à gestão de usuários dentro de um sistema de votação distribuído, com foco em autenticação segura, emissão de tokens JWT e monitoramento de métricas de performance.

## 📌 Função na Arquitetura

* Centraliza autenticação e autorização JWT
* Protege os endpoints de serviços sensíveis via roles (`USER`, `ADMIN`)
* Expõe endpoints de login, cadastro e consulta
* Coleta métricas com **Micrometer + Prometheus** para observabilidade

## ⚙️ Destaques de Engenharia

* Autenticação stateless com **Spring Security + JWT**
* Criptografia de senha com **BCryptPasswordEncoder**
* Observabilidade com **Micrometer** (timers, counters, gauges)
* Log estruturado com **SLF4J** e **logger contextual**
* Validação detalhada de requisições e respostas via DTOs

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

* Registro de usuário com validação de e-mail duplicado
* Login com geração de JWT e validação de credenciais
* Consulta de perfil (`/me`), busca por ID e nome
* Exclusão de usuário por ID ou nome
* Métricas para cada operação: tempo, sucesso, falha, uso

## 🔧 Tecnologias Utilizadas

* Java 21
* Spring Boot 
* Spring Security
* JWT (`io.jsonwebtoken`)
* Spring Data JPA + PostgreSQL
* Micrometer (para Prometheus/Grafana)
* SLF4J

## 🔍 Observabilidade (exemplo de métricas)

* `usuario.listar.todas.chamadas`
* `usuario.buscar.id.tempo`
* `usuario.registro.chamadas`
* `usuario.deletar.nome.naoencontrado`

## 🧪 Endpoints

| Método | URI                 | Descrição                        |
| ------ | ------------------- | -------------------------------- |
| POST   | `/auth/register`    | Cadastro de novo usuário         |
| POST   | `/auth/login`       | Login e retorno de JWT           |
| GET    | `/users/me`         | Dados do próprio usuário         |
| GET    | `/users/{id}`       | (ADMIN) Buscar usuário por ID    |
| GET    | `/users/{userName}` | (ADMIN) Buscar usuário por nome  |
| DELETE | `/users/{id}`       | (ADMIN) Deletar usuário por ID   |
| DELETE | `/users/{userName}` | (ADMIN) Deletar usuário por nome |

## 🚀 Executando Localmente

### Requisitos

* PostgreSQL rodando (porta 5432)
* JDK 21
* Docker

### Passos

```bash
# Compilar o projeto
./mvnw clean install

# Executar a aplicação
java -jar target/voting-system-user-service.jar
```

## 🔄 Integração JWT

* Após login:

```
Authorization: Bearer <jwt-token>
```

* Tokens são validados em cada requisição com filtro personalizado

## 📚 Documentação Swagger

Acesse `http://localhost:8081/swagger-ui/index.html`

## 📈 Exemplos de Requisição

### Registro

```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123",
  "userName": "joaodasilva"
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
