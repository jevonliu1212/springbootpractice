package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.entity.Employee;
import com.springboot.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Employee getById(@PathVariable("id")long id){
		return employeeService.getById(id);
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void add(@RequestBody Employee e){
		employeeService.add(e);
	}
}
