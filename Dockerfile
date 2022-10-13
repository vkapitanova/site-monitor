FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
ARG CERTS_DIR=/tmp/certs
COPY ${JAR_FILE} app.jar
COPY /certs /tmp/certs
ENTRYPOINT ["java","-jar", \
    "-Dspring.profiles.active=${ACTIVE_PROFILES:producer,consumer,web}", \
    "-Dmonitor.url=${MONITOR_URL:https://aiven.io}", \
    "-Dmonitor.regex=${MONITOR_REGEX:}", \
    "-Dspring.datasource.url=${JDBC_URL}", \
    "-Dspring.datasource.username=${DB_USER}", \
    "-Dspring.datasource.password=${DB_PASSWORD}", \
    "-Dspring.kafka.bootstrap-servers=${KAFKA_URL}", \
    "-Dspring.kafka.ssl.trust-store-location=file:/tmp/certs/client.truststore.jks", \
    "-Dspring.kafka.ssl.key-store-location=file:/tmp/certs/client.keystore.p12", \
    "-Dspring.kafka.ssl.trust-store-password=${TRUSTSTORE_PASSWORD}", \
    "-Dspring.kafka.ssl.key-store-password=${KEYSTORE_PASSWORD}", \
    "/app.jar"]
