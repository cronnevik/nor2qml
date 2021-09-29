# Build stage
FROM maven:3.6.1-jdk-8-alpine as build

WORKDIR /app

ARG APP_NAME

# Copy parent files
COPY ../../pom.xml .

# Copy child modules needed
COPY ../../seisan-quakeml-commons-jpa ./seisan-quakeml-commons-jpa
COPY ../../converter-core ./converter-core
COPY ../../seisan-quakeml-commons-web ./seisan-quakeml-commons-web
COPY ../../quakeml-web-service ./quakeml-web-service

# Build the application with maven
RUN mvn clean install -P ws-container -Dspring.profiles.active=container

# Deployment stage
FROM tomcat:9.0.12-jre8 as production
ARG APP_NAME
EXPOSE 8080
RUN rm -f /usr/local/tomcat/conf/tomcat-users.xml
COPY /devops/tomcat/tomcat-users.xml /usr/local/tomcat/conf
COPY --from=build /app/quakeml-web-service/target/quakeml-webservice.war /usr/local/tomcat/webapps/${APP_NAME}.war
