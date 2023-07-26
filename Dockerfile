FROM maven:3.8.6-openjdk-18-slim
LABEL author="Matvei Morenkov"

# 1. Add pom.xml only here

ADD ./pom.xml ./pom.xml

# 2. Start downloading dependencies

RUN ["mvn", "verify", "clean", "--fail-never"]

# 3. Add all source code and start compiling

ADD ./src ./src

RUN ["mvn", "package"]

EXPOSE 8080

