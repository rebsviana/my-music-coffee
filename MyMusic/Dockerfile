FROM maven:3.8.3-openjdk-11 as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn package

FROM openjdk:11
COPY target/summit-bootcamp-0.0.1-SNAPSHOT.jar summit-bootcamp.jar
ENTRYPOINT ["java", "-jar", "summit-bootcamp.jar"]