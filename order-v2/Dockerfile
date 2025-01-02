FROM openjdk:17-jdk-alpine
RUN mkdir /app-order
WORKDIR /app-order
COPY target/*.jar /app-order/order.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app-order/order.jar"]

