# Stage 1: Build
# Use a base image that contains JDK if needed, but no Gradle installation
FROM amazoncorretto:17-alpine AS build
WORKDIR /app

# Copy the built JAR file from your local build directory
COPY build/libs/*. /app/app.jar

# Stage 2: Run
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]