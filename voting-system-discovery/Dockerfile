# Build com Maven e Java 21 

FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app 
COPY pom.xml . 
COPY src ./src
RUN mvn clean package -DskipTests 

# Runtime com JDK 21 leve
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8761


ENV SPRING_APPLICATION_NAME=voting-system-discovery
ENV EUREKA_CLIENT_REGISTER_WITH_EUREKA=false
ENV EUREKA_CLIENT_FETCH_REGISTRY=false
ENV EUREKA_SERVER_WAIT_TIME_IN_MS_WHEN_SYNC_EMPTY=0
ENV LOGGING_LEVEL_COM_NETFLIX_DISCOVERY=INFO
ENV LOGGING_LEVEL_COM_NETFLIX_EUREKA=INFO

ENTRYPOINT ["java", "-jar", "app.jar"]