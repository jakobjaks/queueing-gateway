FROM openjdk:11

FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install

COPY config.yml /var/queueing-gateway/
COPY target/queue-gateway-1.0.jar /var/queueing-gateway/
EXPOSE 8080
WORKDIR /var/queueing-gateway
CMD ["java", "-jar", "-Done-jar.silent=true", "queue-gateway-1.0.jar", "server", "config.yml"]