FROM openjdk:11.0.6-jre-slim-buster

ENV PROFILE docker

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/report_service_0.0.1.jar

EXPOSE 8051:8051

ENTRYPOINT exec java $JAVA_OPTS -Dspring.profiles.active=$PROFILE -jar /app/report_service_0.0.1.jar