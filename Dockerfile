FROM openjdk:11.0.12-slim
EXPOSE 8082
ARG JAR_FILE=build/libs/jibber-jabber-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-jar","/app.jar"]