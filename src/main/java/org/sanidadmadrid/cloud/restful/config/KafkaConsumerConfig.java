package org.sanidadmadrid.cloud.restful.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.sanidadmadrid.cloud.restful.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;
import org.springframework.kafka.support.serializer.JsonDeserializer;

//@ConditionalOnProperty(value = "aplicacion.kafka.enable-consumers", matchIfMissing = false)
@Configuration
public class KafkaConsumerConfig {

	// @Value("${io.reflectoring.kafka.bootstrap-servers}")
	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	@Bean
	public Map<String, Object> consumerJsonConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
		// JsonDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
		props.put(ErrorHandlingDeserializer2.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
		props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Employee.class);

		return props;
	}

	@Bean
	public ConsumerFactory<String, Employee> consumerJsonFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerJsonConfigs());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Employee>> kafkaListenerJsonContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Employee> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerJsonFactory());
		factory.setReplyTemplate(kafkaTemplate);
		// factory.setRecordFilterStrategy(record ->
		// record.value().contains("ignored"));
		return factory;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

}
