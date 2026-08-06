FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src/main/resources/kafka/aiven-truststore.jks /app/kafka/aiven-truststore.jks
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]