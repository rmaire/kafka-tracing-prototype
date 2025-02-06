package ch.uprisesoft.prototype.kafka_consumer_proto.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;


@Service
public class CustomerIdProducerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerIdProducerService.class);

	private final ReactiveKafkaProducerTemplate<String, CustomerId> reactiveKafkaProducer;

	public CustomerIdProducerService(ReactiveKafkaProducerTemplate<String, CustomerId> reactiveKafkaProducer) {
		this.reactiveKafkaProducer = reactiveKafkaProducer;
		log.info("CustomerIdProducerService init");
	}

	public void send(CustomerId message) {
		log.info("send to topic={}, {}={},", "customer-created", CustomerId.class.getSimpleName(), message);
		reactiveKafkaProducer.send("customer-created", message)
				.doOnSuccess(senderResult -> log.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()))
				.subscribe();
	}
}
