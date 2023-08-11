package org.sanidadmadrid.cloud.restful.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;

@Configuration
public class CircuitBreakerConfig {
	
	
	@Bean
	public CircuitBreaker circuitBreaker() {
		
		return CircuitBreaker.of("mi-primer-cb", this.buildConfig());
		
	}

	private io.github.resilience4j.circuitbreaker.CircuitBreakerConfig buildConfig() {
		// TODO Auto-generated method stub
		return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
				.slidingWindowType(SlidingWindowType.COUNT_BASED)
				.slidingWindowSize(4)
				.failureRateThreshold(50f)
				.permittedNumberOfCallsInHalfOpenState(2)
				.waitDurationInOpenState(Duration.ofSeconds(5))
				.writableStackTraceEnabled(false)
				.build();
	}
	private io.github.resilience4j.circuitbreaker.CircuitBreakerConfig buildConfig2() {
		// TODO Auto-generated method stub
		return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
				.slidingWindowType(SlidingWindowType.COUNT_BASED)
				.slidingWindowSize(4)
				.failureRateThreshold(50f)
				.permittedNumberOfCallsInHalfOpenState(2)
				.waitDurationInOpenState(Duration.ofSeconds(5))
				.automaticTransitionFromOpenToHalfOpenEnabled(true)
				.writableStackTraceEnabled(false)
				.build();
	}
	private io.github.resilience4j.circuitbreaker.CircuitBreakerConfig buildConfig3() {
		// TODO Auto-generated method stub
		return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
				.slidingWindowType(SlidingWindowType.COUNT_BASED)
				.slidingWindowSize(4)
				.failureRateThreshold(50f)
				.permittedNumberOfCallsInHalfOpenState(2)
				.waitDurationInOpenState(Duration.ofSeconds(5))
				.writableStackTraceEnabled(false)
				.build();
	}

}
