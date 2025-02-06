package ch.uprisesoft.prototype.kafka_producer_proto;

import ch.uprisesoft.prototype.kafka_producer_proto.producer.Customer;
import ch.uprisesoft.prototype.kafka_producer_proto.producer.CustomerProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
public class KafkaProducerProtoApplication {

	private static final Logger log = LoggerFactory.getLogger(KafkaProducerProtoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerProtoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerProducerService producer) {

		return (args) -> {
			log.info("============= KafkaProducerProtoApplication CommandLineRunner =============");

			Customer c = new Customer("Marvin", "Gaye");
			producer.send(c);
		};

		/*return (args) -> {
			// save a few customers
			repository.saveAll(Arrays.asList(new Customer("Jack", "Bauer"),
							new Customer("Chloe", "O'Brian"),
							new Customer("Kim", "Bauer"),
							new Customer("David", "Palmer"),
							new Customer("Michelle", "Dessler")))
					.blockLast(Duration.ofSeconds(10));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(customer -> {
				log.info(customer.toString());
			}).blockLast(Duration.ofSeconds(10));

			log.info("");

			// fetch an individual customer by ID
			repository.findById(1L).doOnNext(customer -> {
				log.info("Customer found with findById(1L):");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			}).block(Duration.ofSeconds(10));


			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Bauer").doOnNext(bauer -> {
				log.info(bauer.toString());
			}).blockLast(Duration.ofSeconds(10));;
			log.info("");
		};*/
	}

}
