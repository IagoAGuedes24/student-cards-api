FROM openjdk:11
COPY target/*.jar student-cards.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "student-cards.jar"]