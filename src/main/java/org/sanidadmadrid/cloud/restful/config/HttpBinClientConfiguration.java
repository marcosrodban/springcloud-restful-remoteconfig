package org.sanidadmadrid.cloud.restful.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Component;

@Component
public class HttpBinClientConfiguration {
  
  @Autowired
  private ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory;


  public ReactiveCircuitBreaker apply(String reactiveFeignClientName) {
    String circuitBreakerId = reactiveFeignClientName.replaceAll("[#(,]+", "_")
        .replaceAll("\\)+", "")
        .replaceAll("_+$", "");
    return reactiveResilience4JCircuitBreakerFactory.create(circuitBreakerId);
  }
}