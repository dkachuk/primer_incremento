FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# compilar antes con MAVEN o GRADLE
# Para compilar con Maven, usa el siguiente comando:
# mvn clean package -DskipTests
