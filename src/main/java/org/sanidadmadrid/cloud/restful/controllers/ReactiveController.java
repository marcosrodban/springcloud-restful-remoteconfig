package org.sanidadmadrid.cloud.restful.controllers;

import java.util.List;
import java.util.concurrent.Flow.Publisher;

import org.sanidadmadrid.cloud.restful.data.Jugadores;
import org.sanidadmadrid.cloud.restful.services.impl.ReactiveServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController(value = "reactive")
@RequestMapping("/reactive")
public class ReactiveController {

	private static Logger LOGGER = LoggerFactory.getLogger(ReactiveController.class);
	
	@Autowired
	ReactiveServiceImpl reactiveService;
	
	@GetMapping("/info")
	String callCircuitBreakerInfo() {
		LOGGER.info(String.format("LLAMANDO AL SERVICIO INFO CON CIRCUITBREAKER "));
		return "ok";
	}
	
    @GetMapping("/greetings")
    public Publisher<Jugadores> greetingPublisher() {
       // Flux<Greeting> greetingFlux = Flux.<Greeting>generate(sink -> sink.next(new Greeting("Hello"))).take(50);
        List<Jugadores> lista= reactiveService.jugadores().collectList().block();
        
        return lista;
    }
}
