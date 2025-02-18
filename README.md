O sistema permitirá que usuários se cadastrem, criem votações, votem em tempo real e visualizem os resultados atualizados automaticamente.

✅ Um sistema onde os usuários podem:

Criar uma votação com múltiplas opções.
Votar em tempo real (sem precisar atualizar a página).
Acompanhar a contagem de votos ao vivo.
Receber notificações de eventos importantes.
Ter segurança na autenticação e validação dos votos.


🛠 Arquitetura da Aplicação

A aplicação segue uma arquitetura de microsserviços com comunicação via API Gateway e mensageria assíncrona (Kafka/RabbitMQ) para performance otimizada.

🔗 Fluxo Geral da Aplicação

1️⃣ O frontend (React) envia requisições para o API Gateway, que redireciona para os microsserviços adequados.
2️⃣ O User Service gerencia cadastro, login e autenticação JWT.
3️⃣ O Vote Service processa e armazena os votos no PostgreSQL.
4️⃣ O WebSocket + Redis permite que todos os usuários vejam os votos em tempo real.
5️⃣ O Notification Service envia alertas (ex: "Nova votação criada").
6️⃣ O Frontend exibe os resultados dinâmicos, sem precisar atualizar a página.

📌 Microsserviços e seus papéis

🔹 1. API Gateway (voting-system-api-gateway)

Controla o tráfego entre o frontend e os microsserviços.
Faz autenticação JWT e segurança das requisições.
Permite escalabilidade e balanceamento de carga.
O frontend chama a rota e o API Gateway redireciona para user-service.

🔹 2. Serviço de Descoberta (voting-system-discovery)

Usa Eureka Server para registrar microsserviços.
Permite que os serviços se descubram sem configurar IPs fixos.
Quando um serviço sobe, ele se registra no Eureka.
Se cair, o Eureka avisa para o API Gateway.

🔹 3. Serviço de Usuários (voting-system-user-service)

Gerencia cadastro e login de usuários.
Gera tokens JWT para autenticação.
Permite criação de votações.
Usuário se cadastra e faz login.
O sistema retorna um JWT Token.
Com esse token, o usuário pode criar uma votação ou votar.

🔹 4. Serviço de Votos (voting-system-vote-service)

Processa votos em tempo real.
Armazena cada voto no PostgreSQL.
Usa WebSocket + Redis para atualizar a contagem de votos em tempo real.
O usuário envia um voto (/api/votes/{voteId} → Vote Service recebe).
O serviço salva o voto no banco e publica um evento via Kafka/RabbitMQ.
O frontend recebe a atualização via WebSocket.

🔹 5. Serviço de Notificações (voting-system-notification-service)

Envia notificações sobre eventos importantes (nova votação, fim da votação).
Pode usar Kafka, RabbitMQ ou WebSocket.
Um usuário cria uma votação.
O sistema publica um evento "Nova votação criada".
O Notification Service envia um e-mail ou notificação para todos os usuários.

🔹 6. Frontend (voting-system-frontend)

Exibir interface amigável para criação de votações e votação ao vivo.
Consumir APIs do backend via React + Axios.
Exibir a contagem de votos em tempo real com WebSocket.

✅ Tela de Login → Cadastro e autenticação de usuários.
✅ Tela de Criar Votação → Formulário para definir opções de voto.
✅ Tela de Votação → Botões para votar, exibição da contagem em tempo real.
✅ Tela de Resultados → Gráficos e porcentagens dos votos.
