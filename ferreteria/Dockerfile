# Imagen base
FROM eclipse-temurin:25-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiamos el jar
COPY target/ferreteria-0.0.1-SNAPSHOT.jar app.jar


# Puerto de la app
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]