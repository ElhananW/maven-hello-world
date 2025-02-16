# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Optimize caching by installing dependencies first
COPY myapp/pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project and build
COPY myapp/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine AS runtime

# Create a non-root user
RUN addgroup -S appgroup && adduser -S Elhanan -G appgroup

WORKDIR /home/Elhanan/app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Set permissions securely
RUN chown Elhanan:appgroup app.jar && chmod 755 app.jar

# Switch to non-root user
USER Elhanan

# Expose the port
EXPOSE 8080

# Run the JAR file and keep the container alive
ENTRYPOINT ["sh", "-c", "java -jar app.jar && tail -f /dev/null"]
