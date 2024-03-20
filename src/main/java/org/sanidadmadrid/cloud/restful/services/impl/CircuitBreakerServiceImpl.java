package org.sanidadmadrid.cloud.restful.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.sanidadmadrid.cloud.restful.data.User;
import org.sanidadmadrid.cloud.restful.objs.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Service
public class CircuitBreakerServiceImpl {

	private static final List<User> Collection = null;
	private final Logger logger = LoggerFactory.getLogger(CircuitBreakerServiceImpl.class);

	@Autowired
	@Qualifier("customCircuitBreaker3")
	private CircuitBreaker circuitBreaker;

	@Autowired
	@Qualifier("circuitBreakerRegistry")
	private CircuitBreakerRegistry circuitBreakerRegistry;

	public List<User> callCircuitBreakerMock(boolean error) {
		logger.info("Llamamos al servicio callCircuitBreakerMock error:{}", error);

		try {
			if (error) {
				throw new RuntimeException();
			} else {
				List<User> usurios = new ArrayList<User>();
				User usu = new User();
				usu.setApellido("rodrigeuz");
				usu.setName("marcos");
				usurios.add(usu);
				return usurios;
			}
		} catch (Exception e) {
			logger.error("Error llamando al servicio callCircuitBreakerMock error:{}", error);
			throw new RuntimeException(String.format("error al llamar al servicio [url:%s],params:[%s]",
					"http://afjalfdklñafj", "param1,param2,param3"));
		}
	}

	public ResponseEntity callCircuitBreakerInsideMock(boolean error) {
		Supplier<List<User>> productsSupplier = () -> callCircuitBreakerMock(error);

		try {
			CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("example");
			CircuitBreaker cb3 = circuitBreakerRegistry.circuitBreaker("example3");
			logger.info("CircuitBreakerConfig of yml config:{}", cb.getCircuitBreakerConfig());
			logger.info("CircuitBreakerConfig of yml config3:{}", cb3.getCircuitBreakerConfig());
			logger.info("CircuitBreakerConfig:{}", circuitBreaker.getCircuitBreakerConfig());

			Supplier<List<User>> decoratedProductsSupplier = circuitBreaker.decorateSupplier(productsSupplier);
			ResponseEntity<List<User>> re = new ResponseEntity<List<User>>(decoratedProductsSupplier.get(),
					HttpStatus.OK);
			return re;
		} catch (Exception e) {
			return new ResponseEntity<ResponseError>(new ResponseError("E001", "Error al invocar al sistema remoto"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
