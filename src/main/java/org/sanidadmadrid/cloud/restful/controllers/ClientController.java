package org.sanidadmadrid.cloud.restful.controllers;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

	@Value("${aplicacion.texto}")
	String texto;
	
	@Autowired
	private EurekaClient eurekaClient;
//	
	@Value("${aplicacion.zuulconfig.host}")
	String host;
	
	@Value("${aplicacion.zuulconfig.port}")
	String port;

//	@RequestMapping("/dashboard/{myself}")
//	public EmployeeInfo findme(@PathVariable Long myself) {
//		Application application = eurekaClient.getApplication(employeeSearchServiceId);
//		InstanceInfo instanceInfo = application.getInstances().get(0);
//		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "employee/find/" + myself;
//		System.out.println("URL" + url);
//		EmployeeInfo emp = restTemplate.getForObject(url, EmployeeInfo.class);
//		System.out.println("RESPONSE " + emp);
//		return emp;
//	}
	
	@GetMapping("/app/{app}/uri/{uri}")
	public String find(@PathVariable String app,@PathVariable String uri) {
		LOGGER.info(String.format("app:[%s]- uri:[%s]", app, uri));
		RestTemplate restTemplate = new RestTemplate();
		Application application = eurekaClient.getApplication(app);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + host + ":" + port +"/"+app+ "/employee/employees/6";// + uri;
		LOGGER.info(String.format("url:[%s]", url));
		
//		Collection list = 
//		System.out.println("RESPONSE " + list);
		return restTemplate.getForObject(url, String.class);
	}	

//	@RequestMapping("/dashboard/peers")
//	public Collection<EmployeeInfo> findPeers() {
//		Application application = eurekaClient.getApplication(employeeSearchServiceId);
//		InstanceInfo instanceInfo = application.getInstances().get(0);
//		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "employee/findall";
//		System.out.println("URL" + url);
//		Collection<EmployeeInfo> list = restTemplate.getForObject(url, Collection.class);
//		System.out.println("RESPONSE " + list);
//		return list;
//	}

}
