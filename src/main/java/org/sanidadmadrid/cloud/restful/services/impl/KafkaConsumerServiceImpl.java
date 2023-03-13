package org.sanidadmadrid.cloud.restful.services.impl;

import java.io.IOException;
import java.util.List;

import org.sanidadmadrid.cloud.restful.data.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl {



	    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

	    @KafkaListener(topics = "${aplicacion.kafka.topic-name}", groupId = "${aplicacion.kafka.group-id}",
	    		autoStartup = "${aplicacion.kafka.enable-consumers:false}")
	    public void consume(String message) throws IOException {
	        logger.info(String.format("#### -> Consumed String message -> %s", message));
	    }
	    
	  /*  @KafkaListener(topics = "${aplicacion.kafka.topic-json-name}", groupId = "${aplicacion.kafka.group-id}",containerFactory = "kafkaListenerJsonContainerFactory")
	    @SendTo("${aplicacion.kafka.topic-name}")
	    public String consumeJson(Employee message) throws IOException {
	        logger.info(String.format("#### -> Consumed message -> %s", message));
	        return "MENSAJE TRATADO OK";
	    }
	    */
	    
	    @KafkaListener(topics = "${aplicacion.kafka.topic-json-name}", groupId = "${aplicacion.kafka.group-id}",containerFactory = "kafkaListenerJsonContainerFactory",
	    		autoStartup = "${aplicacion.kafka.enable-consumers:false}")
	    @SendTo("${aplicacion.kafka.topic-name}")
	    public String consumeJson(@Payload Employee payloads,
                @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Long> partitionIds,
                @Header(KafkaHeaders.OFFSET) List<Long> offsets) throws IOException {
	        logger.info(String.format("#### -> Consumed message -> %s", payloads));
	        return "MENSAJE TRATADO OK";
	    }
	    
	    @KafkaListener(topics = "${aplicacion.kafka.topic-json-name}", groupId = "${aplicacion.kafka.group-id}",containerFactory = "kafkaListenerJsonContainerFactory",
	    		autoStartup = "${aplicacion.kafka.enable-consumers:false}")
	    @SendTo("${aplicacion.kafka.topic-name}")
	    public String consumeJsonHeaders(@Payload Employee payloads,
	    		@Headers MessageHeaders messageHeaders) throws IOException {
	        logger.info(String.format("#### -> Consumed message -> %s", payloads));
	        
	        
	        logger.info("- - - - - - - - - - - - - - -");
	        
	        logger.info("received message='{}'", payloads);
	        messageHeaders.keySet().forEach(key -> {
	            Object value = messageHeaders.get(key);
	            if (key.equals("X-Custom-Header")){
	            	logger.info("{}: {}", key, new String((byte[])value));
	            } else {
	            	logger.info("{}: {}", key, value);
	            }
	        });
	        return "MENSAJE TRATADO OK";

	    }
	   
	    

}
