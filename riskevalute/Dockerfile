# Use a lightweight Java Runtime (JRE)
FROM eclipse-temurin:25-jdk

# Set a working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
# Note: Ensure your maven build creates a jar in the /target folder
COPY target/*.jar app.jar

# Expose port 8080 (The port Spring Boot runs on)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]