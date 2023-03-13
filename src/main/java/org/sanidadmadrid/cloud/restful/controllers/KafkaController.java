package org.sanidadmadrid.cloud.restful.controllers;


import org.sanidadmadrid.cloud.restful.data.Employee;
import org.sanidadmadrid.cloud.restful.data.User;
import org.sanidadmadrid.cloud.restful.services.impl.KafkaPublisherServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);
	
	
	
	@Autowired
	private KafkaPublisherServiceImpl kafkaPubliserService;

	@GetMapping("/producer/{id}")
	Employee producer(@PathVariable Long id) {
		this.kafkaPubliserService.sendMessage("nuevo mensaje"+id);
		Employee e = new Employee();
		e.setName("MARCOS");
		e.setRole("JEFE");
		e.setId((long) 0);
		return e;
	}

	
	@GetMapping("/producerjson/{id}")
	Employee producerJson(@PathVariable Long id) {
		 
		Employee e = new Employee();
		e.setName("MARCOS");
		e.setRole("JEFE");
		e.setId((long) 0);
		this.kafkaPubliserService.sendMessageJson(e);
		return e;
	}
	
	@GetMapping("/producerjsonmessage/{id}")
	Employee producerJsonMessage(@PathVariable Long id) {
		 
		Employee e = new Employee();
		e.setName("MARCOS");
		e.setRole("JEFE");
		e.setId((long) 0);
		this.kafkaPubliserService.sendMessageKafkaMessage(e);
		return e;
	}
	
	@GetMapping("/produceruserjson/{id}")
	User producerUserJson(@PathVariable Long id) {
		 
		User e = new User();
		e.setName("MARCOS");
		e.setApellido("JEFE");
		e.setId(id);
		this.kafkaPubliserService.sendMessageUserJson(e);
		return e;
	}
	
    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.kafkaPubliserService.sendMessage(message);
    }
	


}
