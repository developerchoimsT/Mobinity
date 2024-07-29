# Stage 1: Build
FROM gradle:8.8-jdk17 AS build
WORKDIR /app

# Copy Gradle files and source code
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Make Gradle Wrapper executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew bootJar

# Stage 2: Run
FROM amazoncorretto:17
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Set the entry point for the application
ENTRYPOINT ["java", "-jar", "app.jar"]