# Building the artifact
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml ./
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn clean -e -B package

#
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/friends-add-0.0.1-SNAPSHOT-*-jar-with-dependencies.jar ./app.jar
ENTRYPOINT java -jar app.jar erpk=$ERPK token=$TOKEN from_id=$FROM_ID to_id=$TO_ID \
include_countries=$INCLUDE_COUNTRIES exclude_countries=$EXCLUDE_COUNTRIES \
add_dead=$ADD_DEAD add_banned=$ADD_BANNED add_blocked=$ADD_BLOCKED
