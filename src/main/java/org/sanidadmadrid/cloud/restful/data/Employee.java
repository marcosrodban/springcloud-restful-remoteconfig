package org.sanidadmadrid.cloud.restful.data;




public  class Employee {
	  private  Long id;
	  private String name;
	  private String role;

	  public Employee() {}

	  Employee(String name, String role) {
	    this.name = name;
	    this.role = role;
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

	  
	  


	
}
