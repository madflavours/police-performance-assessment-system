# Simple Dockerfile to run the fat JAR produced by Gradle
ENTRYPOINT ["java","-jar","/app/app.jar"]
COPY build/libs/app.jar /app/app.jar
WORKDIR /app
FROM eclipse-temurin:17-jdk-alpine

