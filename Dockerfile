FROM maven:3.8.6-openjdk-18-slim
LABEL author="Matvei Morenkov"

WORKDIR /app

# COPY pom.xml
COPY pom.xml .
RUN mvn clean package -DskipTests

# COPY source code
COPY target/task-tracker-api-0.0.1-SNAPSHOT.jar /app/task-tracker-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "task-tracker-api.jar"]


### create network
# docker network create task-tracker-net

### create postgres database
# docker run --name postgresdb -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 --network task-tracker-net postgres
# docker exec -it postgresdb createdb -U postgres task-tracker

### run task-tracker-api
# docker build -t task-tracker-api .
# docker run --name task-tracker-api-app -p 8080:8080 --network task-tracker-net task-tracker-api

