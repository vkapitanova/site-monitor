#!/bin/bash
set -e

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

cp -Rf "$CERTS_DIR" .

./mvnw package -Dmaven.test.skip
docker build -t aiven-monitor .
docker stop monitor && docker rm monitor || true
docker run --name monitor -p 8080:8080 --rm \
-e ACTIVE_PROFILES="$ACTIVE_PROFILES" \
-e MONITOR_URL="$MONITOR_URL" \
-e MONITOR_REGEX="$MONITOR_REGEX" \
-e TRUSTSTORE_PASSWORD="$TRUSTSTORE_PASSWORD" \
-e KEYSTORE_PASSWORD="$KEYSTORE_PASSWORD" \
-e JDBC_URL="$JDBC_URL" \
-e DB_USER="$DB_USER" \
-e DB_PASSWORD="$DB_PASSWORD" \
-e KAFKA_URL="$KAFKA_URL" \
aiven-monitor
