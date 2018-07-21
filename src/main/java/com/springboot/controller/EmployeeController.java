package com.springboot.controller;


import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.springboot.Mapper.EmployeeMapper;
import com.springboot.entity.Employee;
import com.springboot.service.AsyncService;
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
	@Autowired
	private AsyncService asyncService;
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Employee getById(@PathVariable("id")long id) throws InterruptedException, ExecutionException{
		logger.info("查询人员信息id={}",id);
		logger.debug("debug...查询人员信息id={}",id);
		
//		Map<String,Object> postData = new HashMap<>();
//		postData.put("id", 1);
//		postData.put("name", "222");
//		postData.put("mobile","444");
//		System.out.println("=========="+restTemplate.postForEntity("http://localhost:8888/emp/update", postData, null));
		
		//Future<String> res = asyncService.asyncTest();
		//logger.info("future====={}",res.get());
		
		return employeeMapper.get(id);
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void add(@Validated@RequestBody Employee e){
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
	
	
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file){
		if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
       
        String path = "E:/test" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        
        try {
			file.transferTo(dest);
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "true";
	}
	
	
}
