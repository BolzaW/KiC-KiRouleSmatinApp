# Dockerfile
# Étape 1 : Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Le port par défaut de Spring Boot
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]