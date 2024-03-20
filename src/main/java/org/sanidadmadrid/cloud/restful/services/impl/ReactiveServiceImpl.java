package org.sanidadmadrid.cloud.restful.services.impl;

import org.sanidadmadrid.cloud.restful.data.Jugadores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class ReactiveServiceImpl {
	private final Logger logger = LoggerFactory.getLogger(ReactiveServiceImpl.class);

	public Flux<Jugadores> jugadores() {
		WebClient cliente = WebClient.create("http://localhost:8080/facturas");
		Flux<Jugadores> facturas = cliente.get().retrieve().bodyToFlux(Jugadores.class);
		Flux<Jugadores> facturas2 = cliente.get().retrieve().bodyToFlux(Jugadores.class);
		Flux<Jugadores> facturas3 = cliente.get().retrieve().bodyToFlux(Jugadores.class);
		Flux<Jugadores> todas = Flux.merge(facturas, facturas2, facturas3);
		System.out.println(todas);
		return todas;
	}

}
