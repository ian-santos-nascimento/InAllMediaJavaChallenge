FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copy pom.xml first to leverage Docker cache
COPY pom.xml .
# Download dependencies (will be cached if pom.xml hasn't changed)
RUN mvn dependency:go-offline -B
# Copy source code
COPY src ./src
# Package the application
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy JAR from build stage
COPY --from=build /app/target/InAllMediaJavaChallenge-0.0.1-SNAPSHOT.jar /app/app.jar
# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]