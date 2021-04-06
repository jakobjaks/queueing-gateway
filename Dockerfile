FROM openjdk:11
COPY config.yml /var/queueing-gateway/
COPY target/queue-gateway-1.0.jar /var/queueing-gateway/
EXPOSE 8080
WORKDIR /var/queueing-gateway
CMD ["java", "-jar", "-Done-jar.silent=true", "queue-gateway-1.0.jar", "server", "config.yml"]