# Use Amazon Corretto 19 as base image
FROM amazoncorretto:19

# Set working directory inside container
WORKDIR /app

# Copy the compiled JAR into the container
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port used by the user service
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
