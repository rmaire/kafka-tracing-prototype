package ch.uprisesoft.prototype.kafka_consumer_proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class KafkaConsumerProtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerProtoApplication.class, args);
		Hooks.enableAutomaticContextPropagation();
	}

}
