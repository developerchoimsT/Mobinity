FROM gradle:8.8-jdk17 AS build
WORKDIR /app

# Copy source code & build
COPY --chown=gradle:gradle . /app

# Make gradlew executable
RUN chmod +x ./gradlew

# Run Gradle build
RUN ./gradlew build -x test

# Stage 2: Run
FROM amazoncorretto:17-alpine
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]