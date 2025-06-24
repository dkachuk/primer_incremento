# Imagen base de Java 24
FROM openjdk:24-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo jar generado por Maven
COPY target/api-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto que usa tu backend (por ejemplo 8080)
EXPOSE 8484

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
