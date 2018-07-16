package com.springboot.controller;


import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.Mapper.EmployeeMapper;
import com.springboot.entity.Employee;
import com.springboot.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Employee getById(@PathVariable("id")long id){
		return employeeService.getById(id);
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void add(@RequestBody Employee e){
		employeeService.add(e);
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void update(@RequestBody Employee e){
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
