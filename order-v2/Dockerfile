FROM openjdk:17-jdk-alpine
RUN mkdir /app-order
WORKDIR /app-order
COPY target/*.jar /app-order/order.jar
CMD ["java", "-jar", "/app-order/order.jar"]

