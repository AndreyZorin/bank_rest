# Use a base image with Java 17
FROM openjdk:25

# Copy the JAR package into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bank_rest-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Run the App
ENTRYPOINT ["java", "-jar", "/bank_rest-0.0.1-SNAPSHOT.jar"]