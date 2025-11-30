 # Stage 1: Build the application (if using multi-stage builds)
    FROM maven:3.9.11-openjdk-25 AS build
    WORKDIR /app
    COPY pom.xml .
    COPY src ./src
    RUN mvn clean package -DskipTests

    # Stage 2: Create the final image
    FROM openjdk:25-jdk-slim
    WORKDIR /app
    COPY --from=build /app/target/*.jar app.jar
    EXPOSE 8080
    ENTRYPOINT ["mvn spring-boot:run"]