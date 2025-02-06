package ch.uprisesoft.prototype.kafka_consumer_proto.consumer;


import ch.uprisesoft.prototype.kafka_consumer_proto.producer.CustomerId;
import ch.uprisesoft.prototype.kafka_consumer_proto.producer.CustomerIdProducerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CustomerConsumerService implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(CustomerConsumerService.class);

	private final ReactiveKafkaConsumerTemplate<String, Customer> reactiveKafkaConsumer;
	private final ReactiveKafkaConsumerTemplate<String, CustomerId> reactiveKafkaConsumerId;
	private final CustomerRepository repo;
	private final CustomerIdProducerService idProducer;

	public CustomerConsumerService(ReactiveKafkaConsumerTemplate<String, Customer> reactiveKafkaConsumer, ReactiveKafkaConsumerTemplate<String, CustomerId> reactiveKafkaConsumerId, CustomerRepository repo, CustomerIdProducerService idProducer) {
		this.reactiveKafkaConsumer = reactiveKafkaConsumer;
		this.reactiveKafkaConsumerId = reactiveKafkaConsumerId;
		this.repo = repo;
		this.idProducer = idProducer;
		log.info("CustomerConsumerService init");
		log.info(reactiveKafkaConsumer.toString());
	}

	private Flux<Customer> consumeCustomer() {
		log.info("CustomerConsumerService consume() CUSTOMER");
		return reactiveKafkaConsumer
				.receiveAutoAck()
				.doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
						consumerRecord.key(),
						consumerRecord.value(),
						consumerRecord.topic(),
						consumerRecord.offset())
				)
				.map(ConsumerRecord::value)
				.doOnNext(message -> log.info("successfully consumed {}={}", Customer.class.getSimpleName(), message))
				.doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
	}

	private Flux<CustomerId> consumeCustomerId() {
		log.info("CustomerConsumerService consume() ID");
		return reactiveKafkaConsumerId
				.receiveAutoAck()
				.doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
						consumerRecord.key(),
						consumerRecord.value(),
						consumerRecord.topic(),
						consumerRecord.offset())
				)
				.map(ConsumerRecord::value)
				.doOnNext(message -> log.info("successfully consumed {}={}", CustomerId.class.getSimpleName(), message))
				.doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
	}

	@Override
	public void run(String... args) throws Exception {
		consumeCustomer().subscribe(record -> {
			Customer customer = repo.save(record).block();
			idProducer.send(new CustomerId(customer.getId()));
		});

		consumeCustomerId().subscribe(record -> {
			Long id = record.getId();
			Customer customer = repo.findById(id).block();
			log.info("Got Customer ID: {}. Name: {} {}", record, customer.getFirstName(), customer.getLastName());
		});
	}
}
