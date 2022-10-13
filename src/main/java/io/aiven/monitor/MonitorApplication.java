package io.aiven.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
public class MonitorApplication {

	public static void main(String[] args) throws IOException {
		// workaround to use certificates in kafka running jar file: https://github.com/spring-projects/spring-kafka/issues/710
		FileCopyUtils.copy(new ClassPathResource("certs/client.keystore.p12").getInputStream(),
				new FileOutputStream("/tmp/client.keystore.p12"));
		FileCopyUtils.copy(new ClassPathResource("certs/client.truststore.jks").getInputStream(),
				new FileOutputStream("/tmp/client.truststore.jks"));

		SpringApplication.run(MonitorApplication.class, args);
	}

}
