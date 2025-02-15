# Use official OpenJDK image as base image
FROM openjdk:21-jdk-slim

# Create a non-root user
RUN useradd -ms /bin/bash myuser

# Set the working directory
WORKDIR /home/myuser/app

# Copy the JAR file into the container
COPY myapp/target/*.jar app.jar

# Set the user to the non-root user
USER myuser

# Expose the port the app will run on
EXPOSE 8080

# Run the JAR file and keep the container alive
ENTRYPOINT ["sh", "-c", "java -jar app.jar && tail -f /dev/null"]
