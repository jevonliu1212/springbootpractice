package com.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Value("${info.name}")
	private String name;
	
	@RequestMapping("/")
	public String helloWord(){
		return "Hello World,"+name;
	}
}
