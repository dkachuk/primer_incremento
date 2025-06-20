# Imagen base de Java 24
FROM openjdk:24-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo jar generado por Maven
COPY target/be-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto que usa tu backend (por ejemplo 8080)
EXPOSE 8080

# Variables de entorno para la conexión MQTT
ENV MQTT_BROKER_URL=tcp://190.0.100.10:1883
ENV MQTT_USER=guest
ENV MQTT_PASS=guest

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
