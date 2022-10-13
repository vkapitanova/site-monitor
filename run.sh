#!/bin/bash

#JDBC_URL=
#DB_USER=
#DB_PASSWORD=
#KAFKA_URL=
ACTIVE_PROFILES=${ACTIVE_PROFILES:-'producer,consumer,web'}
MONITOR_URL=${MONITOR_URL:-'https://aiven.io'}
MONITOR_REGEX=${MONITOR_REGEX:-}
CERTS_DIR=${CERTS_DIR:-'/tmp/certs'}
TRUSTSTORE_PASSWORD=${TRUSTSTORE_PASSWORD:-password}
KEYSTORE_PASSWORD=${KEYSTORE_PASSWORD:-password}

./mvnw package && java -jar \
-Dspring.profiles.active="${ACTIVE_PROFILES}" \
-Dmonitor.url="${MONITOR_URL}" \
-Dmonitor.regex="${MONITOR_REGEX}" \
-Dspring.datasource.url="${JDBC_URL}" \
-Dspring.datasource.username="${DB_USER}" \
-Dspring.datasource.password="${DB_PASSWORD}" \
-Dspring.kafka.bootstrap-servers="${KAFKA_URL}" \
-Dspring.kafka.ssl.trust-store-location="file:${CERTS_DIR}/client.truststore.jks" \
-Dspring.kafka.ssl.key-store-location="file:${CERTS_DIR}/client.keystore.p12" \
-Dspring.kafka.ssl.trust-store-password="${TRUSTSTORE_PASSWORD}" \
-Dspring.kafka.ssl.key-store-password="${KEYSTORE_PASSWORD}" \
target/monitor-0.0.1-SNAPSHOT.jar
