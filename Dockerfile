FROM openjdk:22

COPY target/hav.imobiliaria-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
