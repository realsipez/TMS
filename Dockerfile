FROM openjdk:25-slim-bullseye
LABEL authors="Sina Pezeshki"
ARG JAR_FILE=build/libs/TMS-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]