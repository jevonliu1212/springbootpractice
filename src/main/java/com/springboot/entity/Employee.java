package com.springboot.entity;

import org.hibernate.validator.constraints.NotBlank;

public class Employee {

	private Long id;
	
	@NotBlank(message = "name不能为空")
	private String name;
	
	private String mobile;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
