FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY src/main/java/com/example/redshift/App.java src/main/java/com/example/redshift/App.java
COPY pom.xml pom.xml
RUN mvn clean package

FROM eclipse-temurin:21
COPY --from=build /build/target/redshift-debug-1.0-SNAPSHOT.jar redshift.jar
ENTRYPOINT ["java", "-cp", "redshift.jar", "com.example.redshift.App"]
CMD ["conn-string", "user", "password"]
