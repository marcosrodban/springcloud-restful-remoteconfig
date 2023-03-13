FROM openjdk:8-jdk-alpine
MAINTAINER marcos.rod.ban
COPY target/restful-remoteconfig-1.0.jar restful-remoteconfig-1.0.jar
ENTRYPOINT ["java","-jar","/message-server-1.0.0.jar"]