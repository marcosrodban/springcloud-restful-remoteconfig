package org.sanidadmadrid.cloud.restful.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.sanidadmadrid.cloud.restful.data.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

	//@Value("${io.reflectoring.kafka.bootstrap-servers}")
	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	
	
	///////////////////////////////////////////////////////////
	
	
	@Bean
	public Map<String, Object> producerJsonConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return props;
	}

	@Bean
	public ProducerFactory<String, Employee> producerJsonFactory() {
		return new DefaultKafkaProducerFactory<>(producerJsonConfigs());
	}

	@Bean
	public KafkaTemplate<String, Employee> kafkaJsonTemplate() {
		return new KafkaTemplate<>(producerJsonFactory());
	}
	
	///////////////////////////////////////////////////////////
	
	@Bean
	public ProducerFactory<String, Object> producerJsonObjectFactory() {
		return new DefaultKafkaProducerFactory<>(producerJsonConfigs());
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaJsonObjectTemplate() {
		return new KafkaTemplate<>(producerJsonObjectFactory());
	}

}
