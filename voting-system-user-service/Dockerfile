# Dockerfile para usuario_service (Spring Boot com Maven)
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Copia o build gerado pelo Maven para o container
COPY target/voting-system-user-service-0.0.1-SNAPSHOT.jar app.jar


# Expõe a porta padrão do Spring Boot
EXPOSE 8083

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
