FROM openjdk:11
EXPOSE 8080
ADD target/inventory-service.jar inventory-service.jar
ENTRYPOINT ["java",".jar","/inventory-service.jar"]