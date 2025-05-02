# Use Amazon Corretto 19 base image
FROM amazoncorretto:19

# Create and set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (change this per service)
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
