FROM openjdk:11
WORKDIR .
COPY . .
RUN ./mvnw clean package
CMD ["java", "-jar", "target/student-cards-api-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080