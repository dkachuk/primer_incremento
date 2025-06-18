FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/servient.jar servient.jar
ENTRYPOINT ["java", "-jar", "servient.jar"]
