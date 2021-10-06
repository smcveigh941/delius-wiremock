FROM openjdk:16-slim

RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get install -y curl

COPY target/delius-wiremock-1.0.jar delius-wiremock-1.0.jar
ENTRYPOINT ["java","-jar","/delius-wiremock-1.0.jar"]
