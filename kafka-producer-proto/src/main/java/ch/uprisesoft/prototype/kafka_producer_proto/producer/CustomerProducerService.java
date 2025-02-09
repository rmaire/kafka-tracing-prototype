package ch.uprisesoft.prototype.kafka_producer_proto.producer;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderRecord;

import java.nio.charset.StandardCharsets;


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

		ProducerRecord<String, Customer> producerRecord = new ProducerRecord<>("new-customer", message);
		producerRecord
				.headers()
				.add(KafkaHeaders.CORRELATION_ID,
						MDC.get("traceId").getBytes(StandardCharsets.UTF_8));

		reactiveKafkaProducer.send(producerRecord)
				.doOnNext(x -> {
					log.info("======================");
					log.info(MDC.get("traceId"));
					log.info("======================");
				})
				.doOnSuccess(senderResult -> log.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()))
				.contextCapture()
				.subscribe();

		return mCustomer;
	}
}
