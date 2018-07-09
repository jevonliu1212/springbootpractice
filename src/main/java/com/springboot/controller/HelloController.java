package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.config.MyConfig;

@RestController
@EnableConfigurationProperties({MyConfig.class})
public class HelloController {

	@Value("${info.name}")
	private String name;
	
	@Autowired
	private MyConfig config;
	
	@RequestMapping("/")
	public String helloWord(){
		return "Hello World,"+name+","+config.getAge()+","+config.getUuid();
	}
}
