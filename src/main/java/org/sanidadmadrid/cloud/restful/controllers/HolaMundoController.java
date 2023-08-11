package org.sanidadmadrid.cloud.restful.controllers;

import java.util.ArrayList;
import java.util.List;

import org.sanidadmadrid.cloud.restful.config.PropertyConfiguration;
import org.sanidadmadrid.cloud.restful.data.Employee;
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

@RestController(value = "employee")
@RequestMapping("/employee")
public class HolaMundoController {

	private static Logger LOGGER = LoggerFactory.getLogger(HolaMundoController.class);

	@Value("${aplicacion.texto}")
	String texto;

	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	@Autowired
	private PropertyConfiguration propertyConfiguration;

	// Aggregate root

	@GetMapping("/employees")
	List<Employee> all() {
		LOGGER.info(String.format("HOLA MUNDO:[%s]-[%s]", texto, propertyConfiguration.getTexto()));
		return new ArrayList<Employee>();
	}

	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		newEmployee.setId((long) 0);
		LOGGER.info(String.format("HOLA MUNDO:[%s]-[%s]", texto, propertyConfiguration.getTexto()));
		return newEmployee;
	}

	// Single item

	@GetMapping("/employees/{id}")
	Employee one(@PathVariable Long id) {
		LOGGER.info(String.format("HOLA MUNDO:[%s]-[%s]", texto, propertyConfiguration.getTexto()));
		Employee e = new Employee();
		e.setName("MARCOS");
		e.setRole("JEFE");
		e.setId((long) 0);
		return e;
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		newEmployee.setId(id);
		LOGGER.info(String.format("HOLA MUNDO:[%s]-[%s]", texto, propertyConfiguration.getTexto()));
		return newEmployee;

	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		Employee e = new Employee();
		LOGGER.info(String.format("HOLA MUNDO:[%s]-[%s]", texto, propertyConfiguration.getTexto()));

		// repository.delete(id);
	}
	
	@RequestMapping("/listar/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
	
	
}
