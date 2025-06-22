Sistema de votação em tempo real baseado em microsserviços.

## 📌 Descrição Geral

Um sistema completo onde os usuários podem:

* Criar votações com múltiplas opções
* Votar em tempo real
* Acompanhar contagem de votos ao vivo (WebSocket + Redis)
* Receber notificações de eventos (Kafka/RabbitMQ)
* Ter segurança com autenticação JWT

## 🧱 Arquitetura

Arquitetura de microsserviços com comunicação assíncrona via Kafka/RabbitMQ e descoberta de serviços com Eureka.

### Microsserviços

#### 1. API Gateway (`voting-system-api-gateway`)

* Entrada central para todas as requisições
* Valida token JWT
* Encaminha para microsserviços corretos

#### 2. Service Discovery (`voting-system-discovery`)

* Eureka Server
* Registro e descoberta de serviços dinamicamente

#### 3. User Service (`voting-system-user-service`)

* Cadastro e login de usuários
* Gera JWT para autenticação

#### 4. Vote Service (`voting-system-vote-service`)

* Recebe e registra votos no PostgreSQL
* Usa Redis + WebSocket para transmissão em tempo real
* Publica eventos via Kafka/RabbitMQ

#### 5. Notification Service (`voting-system-notification-service`)

* Escuta eventos e envia notificações (email/WebSocket)

#### 6. Frontend (`voting-system-frontend`)

* React com Axios/WebSocket
* Interfaces para login, criação de votações, votação em tempo real e resultados

## 🛠 Tecnologias Utilizadas

* Java 21 + Spring Boot
* Spring Security / Spring Cloud Gateway / Spring Eureka
* PostgreSQL / Redis / RabbitMQ
* Prometheus / Eureka / Grafana / Zipkin
* WebSocket
* Docker / Docker Compose
* React.js

## 🔐 Autenticação JWT

A autenticação é baseada em JWT. Após login, o token deve ser enviado no header:

## 🔄 WebSocket em Tempo Real

Conexões são mantidas para:

* Atualização de contagem de votos
* Notificações push


## 📈 Funcionalidades Frontend

* Tela de Login
* Tela de Criação de Votação
* Votação em tempo real
* Resultados com gráficos dinâmicos

## 🔄 Fluxos Principais

### Votação em tempo real:

```
Frontend → API Gateway → Vote Service → Kafka → Notification Service → WebSocket → Frontend
```

### Autenticação:

```
Frontend → API Gateway → User Service → (JWT) → Frontend
```
