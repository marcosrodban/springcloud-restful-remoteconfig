package org.sanidadmadrid.cloud.restful.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public  class Employee {
	  private  Long id;
	  private String name;
	  private String role;

	  public Employee() {}

	  @JsonCreator
	 public Employee (@JsonProperty("id") Long id,
			  @JsonProperty("name") String name, 
			  @JsonProperty("role") String role) {
	    this.name = name;
	    this.role = role;
	    this.id = id;
	  }
	  


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	public String toString() {
		
		return String.format("Employee: {%s,%s,%s}", id,name,role);	
		
	}
	
	  
	  


	
}
