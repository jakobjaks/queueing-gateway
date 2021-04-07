FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install

FROM openjdk:11
COPY config.yml /var/queueing-gateway/
COPY --from=build /usr/src/app/target/queue-gateway-1.0.jar /var/queueing-gateway/
EXPOSE 8080
WORKDIR /var/queueing-gateway
CMD ["java", "-jar", "-Done-jar.silent=true", "queue-gateway-1.0.jar", "server", "config.yml"]