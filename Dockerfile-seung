FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=seung","-jar","/app.jar"]