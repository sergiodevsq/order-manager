FROM openjdk:17-jdk-alpine
RUN mkdir /app-suborder
WORKDIR /app-suborder
COPY target/*.jar /app-suborder/sub-order.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app-suborder/sub-order.jar"]