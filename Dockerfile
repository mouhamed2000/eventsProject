FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port your app runs on (typically 8080 for Spring Boot)
EXPOSE 8085

# Configure Spring Boot to write its logs to stdout/stderr
ENV JAVA_OPTS="-Dspring.output.ansi.enabled=ALWAYS"

# Add necessary environment variables for actuator
ENV MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE="*" \
    MANAGEMENT_ENDPOINT_PROMETHEUS_ENABLED="true" \
    MANAGEMENT_PROMETHEUS_METRICS_EXPORT_ENABLED="true" \
    MANAGEMENT_ENDPOINTS_WEB_BASE_PATH="/actuator"

# Run the application with proper Java options
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]