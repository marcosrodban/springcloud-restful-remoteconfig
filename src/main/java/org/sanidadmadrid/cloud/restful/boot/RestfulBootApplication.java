package org.sanidadmadrid.cloud.restful.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"org.sanidadmadrid.cloud.restful"})
public class RestfulBootApplication {
	
	  public static void main(String... args) {
		    SpringApplication.run(RestfulBootApplication.class, args);
		  }

}
