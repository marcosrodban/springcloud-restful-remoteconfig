package org.sanidadmadrid.cloud.restful.config;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.timelimiter.TimeLimiter;

@Configuration
public class CircuitBreakerConfig {
	
	
//	@Bean
//	public CircuitBreaker circuitBreaker() {
//		
//		return CircuitBreaker.of("mi-primer-cb", this.buildConfig());
//		
//	}

	/*@Autowired
	@Qualifier("circuitBreakerRegistry")
	private CircuitBreakerRegistry circuitBreakerRegistry;
*/
	 private final Logger logger = LoggerFactory.getLogger(CircuitBreakerConfig.class);
		

	 
	  @Bean
	  public RegistryEventConsumer<CircuitBreaker> circuitBreakerEventConsumer() {
	    return new RegistryEventConsumer<CircuitBreaker>() {

	      @Override
	      public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
	        entryAddedEvent.getAddedEntry().getEventPublisher()
	            .onFailureRateExceeded(event -> logger.error("circuit breaker {} failure rate {} on {}",
	                event.getCircuitBreakerName(), event.getFailureRate(), event.getCreationTime())
	            )
	            .onSlowCallRateExceeded(event -> logger.error("circuit breaker {} slow call rate {} on {}",
	                event.getCircuitBreakerName(), event.getSlowCallRate(), event.getCreationTime())
	            )
	            .onCallNotPermitted(event -> logger.error("circuit breaker {} call not permitted {}",
	                event.getCircuitBreakerName(), event.getCreationTime())
	            )
	            .onError(event -> logger.error("circuit breaker {} error with duration {}s",
	                event.getCircuitBreakerName(), event.getElapsedDuration().getSeconds())
	            )
	            .onStateTransition(
	                event -> logger.warn("circuit breaker {} state transition from {} to {} on {}",
	                    event.getCircuitBreakerName(), event.getStateTransition().getFromState(),
	                    event.getStateTransition().getToState(), event.getCreationTime())
	            );
	      }
	      
	   
	 
	

	      @Override
	      public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
	        entryRemoveEvent.getRemovedEntry().getEventPublisher()
	            .onFailureRateExceeded(event -> logger.debug("Circuit breaker event removed {}",
	                event.getCircuitBreakerName()));
	      }

	      @Override
	      public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
	        entryReplacedEvent.getNewEntry().getEventPublisher()
	            .onFailureRateExceeded(event -> logger.debug("Circuit breaker event replaced {}",
	                event.getCircuitBreakerName()));
	      }
	    };
	  }
	  
	  
	  @Bean
	  public RegistryEventConsumer<TimeLimiter> timeLimiterEventConsumer() {
	    return new RegistryEventConsumer<TimeLimiter>() {
	      @Override
	      public void onEntryAddedEvent(EntryAddedEvent<TimeLimiter> entryAddedEvent) {
	        entryAddedEvent.getAddedEntry().getEventPublisher()
	            .onTimeout(event -> logger.error("time limiter {} timeout {} on {}",
	                event.getTimeLimiterName(), event.getEventType(), event.getCreationTime())
	            );
	      }

	      @Override
	      public void onEntryRemovedEvent(EntryRemovedEvent<TimeLimiter> entryRemoveEvent) {
	        entryRemoveEvent.getRemovedEntry().getEventPublisher()
	            .onTimeout(event -> logger.error("time limiter removed {}",
	                event.getTimeLimiterName())
	            );
	      }

	      @Override
	      public void onEntryReplacedEvent(EntryReplacedEvent<TimeLimiter> entryReplacedEvent) {
	        entryReplacedEvent.getNewEntry().getEventPublisher()
	            .onTimeout(event -> logger.error("time limiter replaced {} ",
	                event.getTimeLimiterName())
	            );
	      }
	    };
	  }
	  
	  @Bean
	  public RegistryEventConsumer<CircuitBreaker> circuitBreakerEventConsumer2() {
	    return new RegistryEventConsumer<CircuitBreaker>() {

	      @Override
	      public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
	        entryAddedEvent.getAddedEntry().getEventPublisher()
	            .onFailureRateExceeded(event -> logger.error("circuit breaker2 {} failure rate {} on {}",
	                event.getCircuitBreakerName(), event.getFailureRate(), event.getCreationTime())
	            )
	            .onSlowCallRateExceeded(event -> logger.error("circuit breaker2 {} slow call rate {} on {}",
	                event.getCircuitBreakerName(), event.getSlowCallRate(), event.getCreationTime())
	            )
	            .onCallNotPermitted(event -> logger.error("circuit breaker2 {} call not permitted {}",
	                event.getCircuitBreakerName(), event.getCreationTime())
	            )
	            .onError(event -> logger.error("circuit breaker2 {} error with duration {}s",
	                event.getCircuitBreakerName(), event.getElapsedDuration().getSeconds())
	            )
	            .onStateTransition(
	                event -> logger.warn("circuit breaker2 {} state transition from {} to {} on {}",
	                    event.getCircuitBreakerName(), event.getStateTransition().getFromState(),
	                    event.getStateTransition().getToState(), event.getCreationTime())
	            );
	      }
	      
	      @Override
	      public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
	        entryRemoveEvent.getRemovedEntry().getEventPublisher()
	            .onFailureRateExceeded(event -> logger.debug("Circuit breaker2 event removed {}",
	                event.getCircuitBreakerName()));
	      }

	      @Override
	      public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
	        entryReplacedEvent.getNewEntry().getEventPublisher()
	            .onFailureRateExceeded(event -> logger.debug("Circuit breaker2 event replaced {}",
	                event.getCircuitBreakerName()));
	      }
	    };
	  }
	  
	/*  @Bean
	  public CircuitBreakerRegistry customCircuitBreaker() {
		  
		 /* io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config = io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
				  .custom()
				  .slidingWindowType(SlidingWindowType.COUNT_BASED)
				  .slidingWindowSize(10)
				  .failureRateThreshold(70.0f)
				  .build();*/
		  
		  
		  
		  /*io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config = io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
				  .custom()
				  .build();
		  CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("example");
		  
		  
		    CircuitBreakerRegistry.of(config,circuitBreakerEventConsumer2());
		    
		  //CircuitBreaker circuitBreaker = registry.circuitBreaker("CircuitBreakerService");
		  

		  //Supplier<List<User>> productsSupplier = () -> circuitBreakerService.callCircuitBreakerMock(); 
		  
		 // Supplier<List<User>> decoratedProductsSupplier = circuitBreaker.decorateSupplier(productsSupplier);

		   //displaySearchResult(decoratedProductsSupplier);
		  
	  }*/
	  
	  @Bean
	  public CircuitBreaker customCircuitBreaker3() {
		  io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config = io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
				  .custom()
				  .slidingWindowType(SlidingWindowType.COUNT_BASED)
				  .slidingWindowSize(10)
				  .failureRateThreshold(70.0f)
				  .build();
		  //CircuitBreakerRegistry.of(config,circuitBreakerEventConsumer2());
		 // CircuitBreaker cb3 = circuitBreakerRegistry.circuitBreaker("example3");
		  //ReactiveResilience4JCircuitBreakerFactory.create("backendA")
		  
		 // CircuitBreakerConfigCustomizer.of(null, null)
		/*  
			@Autowired
			@Qualifier("circuitBreakerRegistry")
			private CircuitBreakerRegistry circuitBreakerRegistry;*/
		  CircuitBreaker cb3 = CircuitBreaker.ofDefaults("example3");
		  cb3.getEventPublisher()
		  .onCallNotPermitted(e -> {logger.debug("Circuit breaker3 name {} , llamada no permitida{}",
	                e.getCircuitBreakerName(),e.getEventType());})
		  .onStateTransition(e -> {logger.debug("Circuit breaker3 name{} transition from{} to{}",
	                e.getCircuitBreakerName(),e.getStateTransition().getFromState(),e.getStateTransition().getToState());})
		  .onError(e -> {logger.error("ha ocurrido un error name{}",e.getCircuitBreakerName()); })
		  
		  
		  
		  ;

		  
		  
		  return cb3;
		  
		  
		 
		  /*

		  Supplier<List<User>> productsSupplier = () -> circuitBreakerService.callCircuitBreakerMock(); 
		  
		  Supplier<List<User>> decoratedProductsSupplier = circuitBreaker.decorateSupplier(productsSupplier);

		   //displaySearchResult(decoratedProductsSupplier);*/
		  //return circuitBreaker;
		  
	  }

	  @Bean
	  public CircuitBreaker customCircuitBreaker2() {
		  
		 /* io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config = io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
				  .custom()
				  .slidingWindowType(SlidingWindowType.COUNT_BASED)
				  .slidingWindowSize(10)
				  .failureRateThreshold(70.0f)
				  .build();*/
		  
		  
		  
		  io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config = io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
				  .custom()
				  .build();
		  //CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("example");
		  
		  CircuitBreakerRegistry cbr =   CircuitBreakerRegistry.of(config,circuitBreakerEventConsumer2());
		  CircuitBreaker circuitBreaker = cbr.circuitBreaker("example");
		  //circuitBreaker.getEventPublisher().onStateTransition(null);
		  
		  
		  return circuitBreaker;
		  
		  
		 
		  /*

		  Supplier<List<User>> productsSupplier = () -> circuitBreakerService.callCircuitBreakerMock(); 
		  
		  Supplier<List<User>> decoratedProductsSupplier = circuitBreaker.decorateSupplier(productsSupplier);

		   //displaySearchResult(decoratedProductsSupplier);*/
		  //return circuitBreaker;
		  
	  }
	  
	/*  public CircuitBreaker customCircuitBreaker2() {
		  return CircuitBreaker.of(null, null)
	  }*/

}
