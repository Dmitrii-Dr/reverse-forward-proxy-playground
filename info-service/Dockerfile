# Use OpenJDK as the base image
FROM --platform=linux/amd64 openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/info-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application

ENTRYPOINT ["java", "-jar", "app.jar"]