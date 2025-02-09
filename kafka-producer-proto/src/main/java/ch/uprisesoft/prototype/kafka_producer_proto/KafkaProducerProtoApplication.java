package ch.uprisesoft.prototype.kafka_producer_proto;

import ch.uprisesoft.prototype.kafka_producer_proto.producer.Customer;
import ch.uprisesoft.prototype.kafka_producer_proto.producer.CustomerProducerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@OpenAPIDefinition
public class KafkaProducerProtoApplication {

	private static final Logger log = LoggerFactory.getLogger(KafkaProducerProtoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerProtoApplication.class, args);
		Hooks.enableAutomaticContextPropagation();
	}

/*	@Bean
	public CommandLineRunner demo(CustomerProducerService producer) {

		return (args) -> {
			log.info("============= KafkaProducerProtoApplication CommandLineRunner =============");

			Customer c = new Customer("Marvin", "Gaye");
			producer.send(c);
		};
	}*/

}
