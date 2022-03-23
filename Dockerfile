FROM alpine

LABEL maintainer="jasperkroes@gmail.com"

RUN apk add openjdk11

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]