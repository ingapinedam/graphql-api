FROM openjdk:17-slim AS build

WORKDIR /app

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Copiar el archivo pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src src
RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

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