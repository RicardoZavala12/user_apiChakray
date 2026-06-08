# Imgen para mi proyecto de spring boot Users API to Chakray Test by Ricardo Zavala :)

# IMG BASE JAVA 21
FROM eclipse-temurin:21-jdk-alpine

# DIRECTORIO DE TRABAJO 
WORKDIR /app

# COPIA JAR AL CONTAINER
COPY target/users_api-0.0.1-SNAPSHOT.jar app.jar

# PORTH PARA EXPONER MI PROYECTO
EXPOSE 8080

# EJECUTAR EL PROYECTO
ENTRYPOINT [ "java", "-jar", "app.jar" ]
