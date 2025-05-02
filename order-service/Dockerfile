# Use Amazon Corretto 19 as base image
FROM amazoncorretto:19

# Set working directory inside container
WORKDIR /app

# Copy the compiled JAR into the container
COPY target/order-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port used by the order service
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
