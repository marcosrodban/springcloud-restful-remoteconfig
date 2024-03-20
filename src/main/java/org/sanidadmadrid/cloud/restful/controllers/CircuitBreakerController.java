package org.sanidadmadrid.cloud.restful.controllers;


import java.time.Duration;

import org.sanidadmadrid.cloud.restful.objs.Response;
import org.sanidadmadrid.cloud.restful.services.impl.CircuitBreakerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@RestController(value = "circuitbreaker")
@RequestMapping("/circuitbreaker")
public class CircuitBreakerController {

	private static Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerController.class);
	private static final String RESILIENCE4J_INSTANCE_NAME = "example";

	private static final String FALLBACK_METHOD = "fallback";


	@Autowired
	private CircuitBreakerServiceImpl cbsi;



	@GetMapping("/info")
	String callCircuitBreakerInfo() {
		LOGGER.info(String.format("LLAMANDO AL SERVICIO INFO CON CIRCUITBREAKER "));
		return "ok";
	}

//	@GetMapping("/call/{id}")
//	int callCircuitBreaker(@PathVariable Integer id) {
//		LOGGER.info(String.format("LLAMANDO AL SERVICIO CON CIRCUITBREAKER codigo:[%s]", id));
//		return circuitBreakerServiceImpl.runApiPostCall(id);
//	}
//
//	@PostMapping("/call")
//	Employee callCircuitBreaker(@RequestBody Employee newEmployee) {
//		newEmployee.setId((long) 0);
//		LOGGER.info(String.format("LLAMANDO AL SERVICIO CON CIRCUITBREAKER codigo_body:[%s]", newEmployee));
//		return newEmployee;
//	}

	@GetMapping(value = "/timeout/{timeout}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
	@TimeLimiter(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
	public Mono<Response<Boolean>> timeout(@PathVariable int timeout) {
		return Mono.just(toOkResponse()).delayElement(Duration.ofSeconds(timeout));
	}

	@GetMapping(value = "/timeDelay/{delay}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
	public Mono<Response<Boolean>> timeDelay(@PathVariable int delay) {
		return Mono.just(toOkResponse()).delayElement(Duration.ofSeconds(delay));
	}

	@GetMapping(value = "/error/{valid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
	public Mono<Response<Boolean>> error(@PathVariable boolean valid) {
		return Mono.just(valid).flatMap(this::toOkResponse);
	}

	
	@GetMapping(value = "/call/{valid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity call(@PathVariable boolean valid) {
		return cbsi.callCircuitBreakerInsideMock(valid);
	}
	
	
	public Mono<Response<Boolean>> fallback(Exception ex) {
		return Mono.just(toResponse(HttpStatus.INTERNAL_SERVER_ERROR, Boolean.FALSE))
				.doOnNext(result -> LOGGER.warn("fallback executed"));
	}

	private Mono<Response<Boolean>> toOkResponse(boolean valid) {
		if (!valid) {
			return Mono.just(toOkResponse());
		}
		return Mono.error(new RuntimeException("error"));
	}

	private Response<Boolean> toOkResponse() {
		return toResponse(HttpStatus.OK, Boolean.TRUE);
	}

	private Response<Boolean> toResponse(HttpStatus httpStatus, Boolean result) {
		return new Response.ResponseBuilder<Boolean>().code(httpStatus.value()).status(httpStatus.getReasonPhrase()).data(result).build();
	}

}
