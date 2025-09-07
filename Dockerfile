# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY service/target/task-logger-1.0-SNAPSHOT.jar task-logger.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "task-logger.jar"]