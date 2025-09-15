# Use a base image with a Java Runtime Environment (JRE)
FROM azul/zulu-openjdk-alpine:21

# Set the working directory inside the container
WORKDIR /app

# Copy the built Spring Boot JAR file into the container
# Assuming your JAR is named 'your-application.jar' and is in the 'target/' directory
ARG JAR_FILE=./build/libs/newssearch-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} newssearch.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Define the command to run your Spring Boot application
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "newssearch.jar"]