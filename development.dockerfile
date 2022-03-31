FROM maven as builder

COPY src /var/src
COPY pom.xml /var

RUN mvn -f /var/pom.xml clean install

FROM openjdk:11

COPY --from=builder /var/target/*.jar ./app

ENTRYPOINT ["java", "-jar", "./app"]