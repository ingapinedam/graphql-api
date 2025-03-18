FROM openjdk:17-slim AS build

WORKDIR /app

# Copia los archivos del proyecto
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Otorga permisos de ejecución al script Maven Wrapper
RUN chmod +x ./mvnw

# Compila el proyecto y omite las pruebas
RUN ./mvnw package -DskipTests

# Segunda etapa: imagen final más ligera
FROM openjdk:17-slim

WORKDIR /app

# Copia solo el JAR compilado de la etapa anterior
COPY --from=build /app/target/*.jar ApiOtel-0.0.1-SNAPSHOT.jar

# Configura la aplicación para permitir acceso remoto a la consola H2
ENV SPRING_H2_CONSOLE_SETTINGS_WEB_ALLOW_OTHERS=true
ENV SPRING_PROFILES_ACTIVE=docker

# Puerto para la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "ApiOtel-0.0.1-SNAPSHOT.jar"]