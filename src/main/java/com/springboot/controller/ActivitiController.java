package com.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.service.ActivitiService;

@RestController
@RequestMapping("/activiti")
public class ActivitiController {

	@Resource
	private ActivitiService activitiService;
	
	@RequestMapping(value = "/start",method = RequestMethod.POST)
	public void startProcess(){
		activitiService.startProcess();
	}
}
