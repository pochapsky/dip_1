FROM openjdk:17-jdk-slim

EXPOSE 5050

ADD target/diploma_Cloud_Storage-0.0.1-SNAPSHOT.jar backend.jar

CMD ["java", "-jar", "backend.jar"]