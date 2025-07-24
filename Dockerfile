

# Dockerfile
FROM maven:3.9.5-eclipse-temurin-21

WORKDIR /spring-boot-soutenence_loic

COPY . /spring-boot-soutenence_loic

RUN mvn clean install -DskipTests

CMD ["mvn", "spring-boot:run"]
