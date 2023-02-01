FROM openjdk:17-jdk-alpine
ADD target/bank-0.0.1-SNAPSHOT-exec.jar /app.jar
CMD ["java","-jar","/app.jar", "--spring.profiles.active=prod"]
EXPOSE 8888