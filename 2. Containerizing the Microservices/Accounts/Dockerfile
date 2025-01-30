#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#Add Maintainer Info
MAINTAINER "Mayank Kapoor"

#Add application's jar to container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Execute the application
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]