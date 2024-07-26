# Stage 1: Build
FROM gradle:8.8-jdk17 AS build

WORKDIR /app

# Copy source code and build
COPY --chown=gradle:gradle . /app
RUN gradle build --no-daemon

# Stage 2: Run
FROM amazoncorretto:17-alpine

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]