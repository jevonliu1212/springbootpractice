package com.springboot.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.springboot.dao.EmployeeDao;
import com.springboot.entity.Employee;
import com.springboot.service.EmployeeService;
@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Cacheable(value = "emp",key="'emp'.concat(#id)") 
	@Override
	public Employee getById(Long id) {
		System.out.println("getById数据库查询");
		return employeeDao.getById(id);
	}

	@Override
	public int add(Employee e) {
		employeeDao.add(e);
		return 0;
	}

}
