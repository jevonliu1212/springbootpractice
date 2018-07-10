package com.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.dao.EmployeeDao;
import com.springboot.entity.Employee;
import com.springboot.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public Employee getById(Long id) {
		return employeeDao.getById(id);
	}

	@Override
	public int add(Employee e) {
		return employeeDao.add(e);
	}

}
