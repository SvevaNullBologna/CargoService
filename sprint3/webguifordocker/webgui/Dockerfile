# Usa JDK/JRE adatto
FROM eclipse-temurin:17.0.5_8-jre-focal

# Crea una cartella di lavoro
WORKDIR /app

# Copia il jar generato dal build Gradle
COPY build/libs/*.jar app.jar

# Esponi la porta della webapp
EXPOSE 8080

# Comando per avviare l'app
ENTRYPOINT ["java","-jar","app.jar"]



#gradle bootJar