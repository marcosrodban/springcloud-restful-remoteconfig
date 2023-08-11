package org.sanidadmadrid.cloud.restful.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;


@Service
public class CircuitBreakerSampleServiceImpl implements InitializingBean{
    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerSampleServiceImpl.class);
	private CircuitBreaker circuitBreaker;
	private Mock mock = new Mock();

	public CircuitBreakerSampleServiceImpl(CircuitBreaker cb) {
		this.circuitBreaker = cb;
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info(String.format("We are using circuitbreaker:%s", circuitBreaker.getName()));
		
	}
	
	
	public int runApiPostCall(int codigo) {
		this.circuitBreaker.getEventPublisher()
		.onSuccess(e -> { logger.info("Event {}",e.getEventType()); })
		.onError(e -> {logger.error("Event {}, error{}",e.getEventType(),e.getThrowable().getMessage());})
		.onCallNotPermitted(e -> {logger.warn("Even, the circuit is open {}",e.getEventType());})
		.onStateTransition(e -> {logger.info("Event {} to {}",e.getEventType(),e.getStateTransition());});
		return Decorators.ofSupplier(() -> this.mock.mockPost(codigo))
				.withCircuitBreaker(this.circuitBreaker)
				//.withFallback(Arrays.asList(RuntimeException.class),this::fallBackRunApiPostCall)
				.get();
		
		
	}
	
	public int fallBackRunApiPostCall(Throwable err) {
		logger.error("fallback is called, with error {}",err);
		return -1;
		
	}

}
