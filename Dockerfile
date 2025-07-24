


FROM maven:3.9.5-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

CMD ["java", "-jar", "target/kilotogo-0.0.1-SNAPSHOT.jar"]


