FROM gradle:8.8-jdk17 AS build
WORKDIR /hoopi

# Copy source code & build
COPY --chown=gradle:gradle ./hoopi /hoopi

# Make gradlew executable
RUN chmod +x ./gradlew

# Run Gradle build
RUN ./gradlew build -x test

# Stage 2: Run
FROM amazoncorretto:17
COPY --from=build /hoopi/build/libs/*.jar /hoopi/app.jar
ENTRYPOINT ["java", "-jar", "/hoopi/app.jar"]