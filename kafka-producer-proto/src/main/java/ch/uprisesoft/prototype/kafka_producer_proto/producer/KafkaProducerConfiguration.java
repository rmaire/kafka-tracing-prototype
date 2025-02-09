package ch.uprisesoft.prototype.kafka_producer_proto.producer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.MicrometerProducerListener;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

	private final ObservationRegistry observationRegistry;

	private static final Logger log = LoggerFactory.getLogger(KafkaProducerConfiguration.class);

	public KafkaProducerConfiguration(ObservationRegistry observationRegistry) {
		this.observationRegistry = observationRegistry;
	}

	@Bean
	public ReactiveKafkaProducerTemplate<String, Customer> reactiveKafkaProducer(KafkaProperties properties) {

		/*MeterRegistry registry = new SimpleMeterRegistry();
		MicrometerProducerListener producerListener = new MicrometerProducerListener(registry);*/

		Map<String, Object> props = properties.buildProducerProperties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		SenderOptions<String, Customer> options = SenderOptions.create(props);
		options.withObservation(observationRegistry);
//		options.producerListener(producerListener);
		ReactiveKafkaProducerTemplate<String, Customer> template = new ReactiveKafkaProducerTemplate<String, Customer>(options);

		log.info("============== reactiveKafkaProducer() ==============");

		return template;
	}
}
