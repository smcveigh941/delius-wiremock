FROM openjdk:16-alpine3.13
COPY target/delius-nomis-wiremock-1.0.jar delius-nomis-wiremock-1.0.jar
ENTRYPOINT ["java","-jar","/delius-nomis-wiremock-1.0.jar"]
