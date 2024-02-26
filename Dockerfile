FROM openjdk:23-slim-bullseye
RUN apt-get update && apt-get install -y maven
COPY . .
RUN mvn clean install -DskipTests
ENTRYPOINT [ "java", "-jar", "target/finance-0.0.1-SNAPSHOT.jar" ]
