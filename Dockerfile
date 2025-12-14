# -------- BUILD STAGE --------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw -B package -DskipTests

# -------- RUNTIME STAGE --------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/test-java-izertis.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]