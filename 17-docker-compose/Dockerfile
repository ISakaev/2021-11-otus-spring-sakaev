FROM openjdk:11
ARG JAR_FILE=target/17-docker-compose-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} 17-docker-compose-1.0-SNAPSHOT.jar
CMD ["java","-jar","/17-docker-compose-1.0-SNAPSHOT.jar"]