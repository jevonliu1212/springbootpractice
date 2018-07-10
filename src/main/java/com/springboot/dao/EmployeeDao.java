package com.springboot.dao;

import com.springboot.entity.Employee;

public interface EmployeeDao {

	public Employee getById(Long id);
}
