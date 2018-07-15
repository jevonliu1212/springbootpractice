package com.springboot.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.springboot.Mapper.EmployeeMapper;
import com.springboot.entity.Employee;
import com.springboot.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
	private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Employee getById(@PathVariable("id")long id){
		logger.info("查询人员信息id={}",id);
		logger.debug("debug...查询人员信息id={}",id);
		
		Map<String,Object> postData = new HashMap<>();
		postData.put("id", 1);
		postData.put("name", "222");
		postData.put("mobile","444");
		System.out.println("=========="+restTemplate.postForEntity("http://localhost:8888/emp/update", postData, null));
		return employeeMapper.get(id);
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void add(@RequestBody Employee e){
		employeeService.add(e);
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void update(@RequestBody Employee e){
//		ResponseEntity<Employee>  res =restTemplate.getForEntity("http://localhost:8888/emp/1", Employee.class);
//		System.out.println("=========="+res.getBody().getName());
		logger.info("參數====={}",JSON.toJSONString(e));
		employeeMapper.update(e);
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public void update(@RequestParam long id){
		employeeMapper.delete(id);
	}
}
