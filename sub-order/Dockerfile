FROM openjdk:17-jdk-alpine
RUN mkdir /app-suborder
WORKDIR /app-suborder
COPY target/*.jar /app-suborder/sub-order.jar
CMD ["java", "-jar", "/app-suborder/sub-order.jar"]