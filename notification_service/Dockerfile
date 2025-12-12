FROM eclipse-temurin:25-jdk

RUN apt-get update && \
        apt-get install -y ca-certificates && \
        update-ca-certificates && \
        rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]