#FROM openjdk:11
#COPY . /usr/src/myapp
#WORKDIR /usr/src/myapp
#RUN javac Main.java
#CMD ["java", "Main"]


FROM openjdk:11
ARG JAR_FILE=target/17-docker-compose-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} 17-docker-compose-1.0-SNAPSHOT.jar
CMD ["java","-jar","/17-docker-compose-1.0-SNAPSHOT.jar"]


#COPY index.html /usr/share/nginx/html/index.html
#EXPOSE 80
#CMD ["nginx", "-g", "daemon off;"]
#
#
#FROM openjdk:17-alpine
#ARG JAR_FILE=target/my-app.jar
#COPY ${JAR_FILE} my-app.jar
#ENTRYPOINT ["java","-jar","/my-app.jar"]
#
#17-docker-compose-1.0-SNAPSHOT.jar