# Stage 1: Build
FROM gradle:8.8-jdk17 AS build
WORKDIR /hoopi
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Make gradlew executable
RUN chmod +x ./gradlew

# Run Gradle build
RUN ./gradlew build -x test

# Stage 2: Run
FROM amazoncorretto:17
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]