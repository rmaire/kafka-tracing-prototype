package ch.uprisesoft.prototype.kafka_producer_proto.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class CustomerProducerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerProducerService.class);

	private final ReactiveKafkaProducerTemplate<String, Customer> reactiveKafkaProducer;

	public CustomerProducerService(ReactiveKafkaProducerTemplate<String, Customer> reactiveKafkaProducer) {
		this.reactiveKafkaProducer = reactiveKafkaProducer;
		log.info("CustomerProducerService init");
	}

	public Mono<Customer> send(Customer message) {
		Mono<Customer> mCustomer = Mono.just(message);
		log.info("send to topic={}, {}={},", "new-customer", Customer.class.getSimpleName(), message);
		reactiveKafkaProducer.send("new-customer", message)
				.doOnSuccess(senderResult -> log.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()))
				.contextCapture()
				.subscribe();

		return mCustomer;
	}
}
