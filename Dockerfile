#build
FROM maven:3-sapmachine-22 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# RUN

FROM openjdk:22
WORKDIR /app

COPY --from=build ./build/target/*.jar ./hav_api.jar

EXPOSE 8082

ENV DATASOURCE_URL  ''
ENV DATASOURCE_PASSWORD  ''
ENV DATASOURCE_USER  ''
ENV S3_REGION  ''
ENV S3_BUCKET_NAME = ''
ENV S3_REGION ''
ENV S3_ACESS_KEY ''
ENV FRONTEND_URL ''
ENV EMAIL_APP_PASSWORD ''
ENV TOKEN_PASSWORD ''
ENV SPRING_PROFILES_ACTIVE 'production'
ENV TZ 'America/Sao_Paulo'

ENTRYPOINT java -jar hav_api.jar

