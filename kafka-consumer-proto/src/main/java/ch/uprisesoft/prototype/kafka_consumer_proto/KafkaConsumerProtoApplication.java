package ch.uprisesoft.prototype.kafka_consumer_proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
public class KafkaConsumerProtoApplication {

	private static final Logger log = LoggerFactory.getLogger(KafkaConsumerProtoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerProtoApplication.class, args);
	}

}
