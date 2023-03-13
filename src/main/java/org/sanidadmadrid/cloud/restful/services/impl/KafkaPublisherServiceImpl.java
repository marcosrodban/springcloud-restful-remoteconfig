package org.sanidadmadrid.cloud.restful.services.impl;


import org.sanidadmadrid.cloud.restful.data.Employee;
import org.sanidadmadrid.cloud.restful.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(KafkaPublisherServiceImpl.class);

	private static final String TOPIC = "TEST";


	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplateUserJson;

	@Autowired
	private KafkaTemplate<String, Employee> kafkaTemplateJson;
	
	@Value("${aplicacion.kafka.topic-name}")
	String topicName;
	
	@Value("${aplicacion.kafka.topic-json-name}")
	String topicJsonName;

	public void sendMessage(String message) {
		logger.info(String.format("#### -> Producing message -> %s", message));
		this.kafkaTemplate.send(topicName, message);
	}

	
	public void sendMessageJson(Employee messageJson) {
		logger.info(String.format("#### -> Producing message -> %s", messageJson));
		this.kafkaTemplateJson.send(topicJsonName, messageJson);
	}
	

	public void sendMessageUserJson(User user) {
		logger.info(String.format("#### -> Producing message -> %s", user));
		this.kafkaTemplateUserJson.send(topicJsonName, user);
	}
	
	
	public void sendMessageKafkaMessage(Employee messageJson) {
		Message<Employee> m = MessageBuilder.withPayload(messageJson)
	            .setHeader(KafkaHeaders.TOPIC, topicJsonName)
	            .setHeader(KafkaHeaders.MESSAGE_KEY, 42)
	            .setHeader(KafkaHeaders.CORRELATION_ID, 56)
	            .setHeader("someOtherHeader", "someValue")
	            .build();
	
		
		logger.info(String.format("#### -> Producing message -> %s", m));
		this.kafkaTemplateUserJson.send(topicJsonName, m);
	}
}
