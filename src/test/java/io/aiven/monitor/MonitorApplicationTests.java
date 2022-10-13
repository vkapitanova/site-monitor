package io.aiven.monitor;

import io.aiven.monitor.dao.MetricsEntity;
import io.aiven.monitor.dao.MetricsRepository;
import io.aiven.monitor.producer.Scheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles({"producer", "consumer"})
@TestPropertySource(properties = {"monitor.url = https://aiven.io", "monitor.regex = Aiven"})
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class MonitorApplicationTests {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private MetricsRepository repository;


	@Test
	void testFullCycle() {
		// Triggering manually a cron job
		scheduler.cronJob();
		// waiting for consumer to consume the message and save it to the database
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		// checking that there's a record in db and it has proper values
		Iterable<MetricsEntity> records = repository.findAll();
		Assertions.assertTrue(records.iterator().hasNext());
		MetricsEntity entity = records.iterator().next();
		Assertions.assertEquals(200, entity.getStatusCode());
		Assertions.assertEquals("https://aiven.io", entity.getSiteUrl());
		Assertions.assertEquals(Boolean.TRUE, entity.getMatchesRegex());
	}

}
