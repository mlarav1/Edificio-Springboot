FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /workspace
RUN mkdir -p /root/.m2 && printf '<settings><mirrors><mirror><id>google-maven-central</id><name>Google Maven Central</name><url>https://maven-central.storage-download.googleapis.com/maven2/</url><mirrorOf>central</mirrorOf></mirror></mirrors></settings>\n' > /root/.m2/settings.xml
COPY pom.xml ./
COPY src ./src
RUN mvn --batch-mode --no-transfer-progress clean package -DskipTests

FROM eclipse-temurin:17-jre

RUN groupadd --system spring && useradd --system --gid spring spring
WORKDIR /app
COPY --from=build --chown=spring:spring /workspace/target/users-management-*.jar app.jar
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
