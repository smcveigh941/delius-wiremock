FROM openjdk:16-alpine3.13
COPY target/delius-wiremock-1.0.jar delius-wiremock-1.0.jar
ENTRYPOINT ["java","-jar","/delius-wiremock-1.0.jar"]
