# Stage 1: Build the application
FROM maven:3.6.3-openjdk-17 as build
COPY src /usr/home/core-service/src
COPY ./pom.xml /usr/home/core-service
RUN mvn -f /usr/home/core-service/pom.xml clean package -DskipTests

# Stage 2: Package the application
FROM openjdk:17-jdk
COPY --from=build /usr/home/core-service/target/*.jar /core-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/core-service.jar"]