package org.sanidadmadrid.cloud.restful.services.impl;

public class Mock {

	public int mockPost(int i) {
		if(i > 5) {
			return 0;
		}else {
			throw new RuntimeException("Error el codigo es menor que 5");
			
		}
	}
	
	
}
