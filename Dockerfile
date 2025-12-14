FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/test-java-izertis.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]