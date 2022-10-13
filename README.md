# Simple site monitor

## How to run

To run the app you need to have connection properties for postgres and for kafka, url that you wish to monitor (default https://aiven.io) and regex to parse (not matched against regex by default).

Connection to postgres:

- JDBC url that includes host, port and database (example: jdbc:postgresql://pg-1-project-1.aivencloud.com:20127/defaultdb)
- Postgres login
- Postgres password

Connection to kafka:

- Connection URL (example: kafka-1-project-1.aivencloud.com:20129)
- Client keystore and truststore with passwords ([instructions to convert Aiven certificates to keystore and truststore](https://docs.aiven.io/docs/products/kafka/howto/keystore-truststore.html))

Application supports 3 profiles: producer, consumer and web.

By default all 3 profiles will be active, which means running app will be producing events, consuming events and providing web interface.

It is also possible to run only producer, only consumer or only web, or any other combinations of active profiles.

Simplest way to run the app is to use `run.sh` script.

### Environment variables for run.sh

- ACTIVE_PROFILES (_mandaroty_, default: prodicer,consimer,web)
- MONITOR_URL (_mandaroty_, default: https://aiven.io)
- MONITOR_REGEX (_optional_, default: none)
- JDBC_URL (_mandaroty_)
- DB_USER (_mandaroty_)
- DB_PASSWORD (_mandaroty_)
- KAFKA_URL (_mandaroty_)
- CERTS_DIR (_mandaroty_, default: /tmp/certs). In that directory there should be client.keystore.p12 and client.truststore.jks
- KEYSTORE_PASSWORD (_mandaroty_, default: password)
- TRUSTSTORE_PASSWORD (_mandaroty_, default: password)

You can edit run.sh file or provide environment variables with other standard means.