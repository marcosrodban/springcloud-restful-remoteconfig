package org.sanidadmadrid.cloud.restful.controllers;


import org.sanidadmadrid.cloud.restful.config.PropertyConfiguration;
import org.sanidadmadrid.cloud.restful.data.Employee;
import org.sanidadmadrid.cloud.restful.services.impl.CircuitBreakerSampleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "circuitbreaker")
@RequestMapping("/circuitbreaker")
public class CircuitBreakerController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerController.class);

	@Autowired
	private PropertyConfiguration propertyConfiguration;
	
	@Autowired
	private CircuitBreakerSampleServiceImpl circuitBreakerServiceImpl;
	
	@GetMapping("/info")
	String callCircuitBreakerInfo() {
		LOGGER.info(String.format("LLAMANDO AL SERVICIO INFO CON CIRCUITBREAKER "));
		return "ok";
	}


	@GetMapping("/call/{id}")
	int callCircuitBreaker(@PathVariable Integer id) {
		LOGGER.info(String.format("LLAMANDO AL SERVICIO CON CIRCUITBREAKER codigo:[%s]", id));
		return circuitBreakerServiceImpl.runApiPostCall(id);
	}

	@PostMapping("/call")
	Employee callCircuitBreaker(@RequestBody Employee newEmployee) {
		newEmployee.setId((long) 0);
		LOGGER.info(String.format("LLAMANDO AL SERVICIO CON CIRCUITBREAKER codigo_body:[%s]", newEmployee));
		return newEmployee;
	}



	

}
