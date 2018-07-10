package com.springboot.dao;

import com.springboot.entity.Employee;

public interface EmployeeDao {

	Employee getById(Long id);
	
	int add(Employee e);
}
