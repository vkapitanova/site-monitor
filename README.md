# Simple site monitor

## Solution description

Solution is using Java / Spring Boot / Spring Web / Spring Security / Spring Data Jpa and flyway / Spring Kafka / Lombok.

Kafka producer works as a cron job for every 5 seconds.

For the sake of simplicity there's only one test written that covers producer/consumer end to end flow without web. Test is using in memory database and in memory kafka.

Application supports 3 profiles: `producer`, `consumer` and `web`. By default all 3 profiles will be active, which means running app will be producing events, consuming events and providing web interface. 
It is also possible to run only producer, only consumer or only web, or any other combinations of active profiles to run on separate machines. 
Again for the sake of simplicity as spring-boot starters are used, so all configuration parameters are required to run every profile, though kafka producer or consumer will not start if profile is inactive.

After running the app web interface is available on `localhost:8080/`. Authentication was a requirement, so it's a hardcoded user and password: user/password
Simple pagination is based of id offset, can only paginate forwards.

Data is simply written in a database table. At some point amount of data will not be manageable, so there can be certain things done like throttling writes or archiving old data to a colder storage like S3. 

## How to run

### Configuration

To run the app you need to have connection properties for postgres and for kafka, url that you wish to monitor (default https://aiven.io) and regex to parse (not matched against regex by default).

Connection to postgres:

- JDBC url that includes host, port and database (example: jdbc:postgresql://pg-1-project-1.aivencloud.com:20127/defaultdb)
- Postgres login
- Postgres password

Connection to kafka:

- Connection URL (example: kafka-1-project-1.aivencloud.com:20129)
- Client keystore and truststore with passwords ([instructions to convert Aiven certificates to keystore and truststore](https://docs.aiven.io/docs/products/kafka/howto/keystore-truststore.html))

### Running

There're 2 scripts to run the app: `run.sh` and `run-docker.sh`. Both have environment variables that need to be defined in a file or provided with other standard means.

`run.sh` script requires java 17 to be installed on your machine, `run-docker.sh` requires docker to be installed on your machine.

Example of the run command without file modification:
```
JDBC_URL='jdbc:postgresql://<host>:<port>/<db_name>' DB_USER=<db_user> DB_PASSWORD='<db_password>' KAFKA_URL='<host>:<port>' CERTS_DIR='<certificates_location>' ./run-docker.sh
```

### Environment variables

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
