# Queue-Gateway

How to start the Queue-Gateway application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/queue-gateway-1.0.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Dockerizing and uploading
---
1. Run `mvn clean install` to build your application
2. Build Dockerfile
`docker build -t queue_gateway . `
3. Tag the iterations
`docker tag 0972ddf8a84d jakobjaks/queue_gateway:firsttry`
4. Push it
`docker push jakobjaks/queue_gateway`
