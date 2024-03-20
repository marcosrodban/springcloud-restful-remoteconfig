package org.sanidadmadrid.cloud.restful.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

public class CustomCircuitBreakerConfig {
	
	
	  @Autowired
	  private CircuitBreakerRegistry circuitBreakerRegistry;
	  
	  @Autowired
	  private TimeLimiterRegistry timeLimiterRegistry;
	  
	  @Bean
	  public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory() {
	    ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory =
	        new ReactiveResilience4JCircuitBreakerFactory();
	    reactiveResilience4JCircuitBreakerFactory.configureCircuitBreakerRegistry(circuitBreakerRegistry);
	    reactiveResilience4JCircuitBreakerFactory.configureDefault(this::createResilience4JCircuitBreakerConfiguration);
	    return reactiveResilience4JCircuitBreakerFactory;
	  }
	  
	  private Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration createResilience4JCircuitBreakerConfiguration(String id) {
	    CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(id);
	    io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfig = circuitBreaker.getCircuitBreakerConfig();
	    TimeLimiterConfig timeLimiterConfig = timeLimiterRegistry.timeLimiter(id)
	        .getTimeLimiterConfig();
	    circuitBreaker.getEventPublisher()
	        .onEvent(event -> System.out.println("Circuit-breaker Event Publisher : " + event));
	    return new Resilience4JConfigBuilder(id)
	        .circuitBreakerConfig(circuitBreakerConfig)
	        .timeLimiterConfig(timeLimiterConfig)
	        .build();
	  }
}
