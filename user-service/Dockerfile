# Use Amazon Corretto 19 as base image
FROM amazoncorretto:19

# Install netcat (nc)
RUN yum install -y nc

# Set working directory inside container
WORKDIR /app

# Copy the compiled JAR into the container
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Copy the wait-for.sh script into the container
COPY scripts/wait-for.sh /wait-for.sh

# Give execute permission to wait-for.sh
RUN chmod +x /wait-for.sh

# Expose the port used by the user service
EXPOSE 8083

# Wait for Redis to be ready and then run the application
ENTRYPOINT ["/wait-for.sh", "redis:6379", "--", "java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
