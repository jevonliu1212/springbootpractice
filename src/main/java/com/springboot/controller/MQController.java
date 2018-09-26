//package com.springboot.controller;
//
//import javax.jms.Destination;
//
//import org.apache.activemq.command.ActiveMQQueue;
//import org.apache.activemq.command.ActiveMQTopic;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.springboot.service.Producer;
//
//@RestController
//public class MQController {
//
//	private final static Logger logger = LoggerFactory.getLogger(MQController.class);
//	
//	@Autowired
//	private Producer producer;
//	
//	@RequestMapping(value = "/mqtest",method = RequestMethod.GET)
//	public String test(){
//		Destination destination = new ActiveMQQueue("test");
//		for(int i=0;i<5;i++){
//			producer.send(destination, "msg"+i);
//		}
//		return "success";
//	}
//	
//	@RequestMapping(value = "/mqtest1",method = RequestMethod.GET)
//	public String test1(){
//		Destination destination = new ActiveMQTopic("testTopic");
//		for(int i=0;i<5;i++){
//			producer.send(destination, "topic"+i);
//		}
//		return "success";
//	}
//}
