FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE_PATH=./build/libs/it-hermes-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=eun","-jar","/app.jar","-Dspring-boot.run.arguments=--password=${DATASOURCE_PASSWORD}"]
