FROM openjdk:22-jdk-slim
ARG project
COPY ./webapp/target/webapp-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod" ,"webapp-1.0-SNAPSHOT.jar"]