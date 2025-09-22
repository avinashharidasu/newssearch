FROM azul/zulu-openjdk-alpine:21

WORKDIR /app

ARG JAR_FILE=./build/libs/newssearch-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} newssearch.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "newssearch.jar"]