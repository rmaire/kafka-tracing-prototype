package ch.uprisesoft.prototype.kafka_consumer_proto.consumer;

import ch.uprisesoft.prototype.kafka_consumer_proto.producer.CustomerId;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaReceiverConfiguration {

	@Bean
	public ReceiverOptions<String, Customer> kafkaCustomerReceiver(KafkaProperties kafkaProperties) {

		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "reactive-kafka");
		config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
		config.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"ch.uprisesoft.prototype.kafka_consumer_proto.consumer.Customer");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		ReceiverOptions<String, Customer> basicReceiverOptions = ReceiverOptions.create(config);
		return basicReceiverOptions.subscription(Collections.singletonList("new-customer"));

	}

	@Bean
	public ReactiveKafkaConsumerTemplate<String, Customer> reactiveKafkaCustomerConsumer(ReceiverOptions<String, Customer> kafkaReceiverOptions) {
		return new ReactiveKafkaConsumerTemplate<String, Customer>(kafkaReceiverOptions);
	}

	@Bean
	public ReceiverOptions<String, CustomerId> kafkaCustomerIdReceiver(KafkaProperties kafkaProperties) {

		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "reactive-kafka");
		config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
		config.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"ch.uprisesoft.prototype.kafka_consumer_proto.producer.CustomerId");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		ReceiverOptions<String, CustomerId> basicReceiverOptions = ReceiverOptions.create(config);
		return basicReceiverOptions.subscription(Collections.singletonList("customer-created"));

	}

	@Bean
	public ReactiveKafkaConsumerTemplate<String, CustomerId> reactiveKafkaCustomerIdConsumer(ReceiverOptions<String, CustomerId> kafkaReceiverOptions) {
		return new ReactiveKafkaConsumerTemplate<String, CustomerId>(kafkaReceiverOptions);
	}

}
