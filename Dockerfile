# -------- BUILD STAGE --------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


# -------- RUN STAGE --------
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /build/target/Dating-v1.0.jar app.jar

EXPOSE 1234

ENTRYPOINT ["java","-jar","app.jar"]
