//package com.springboot.service;
//
//import javax.jms.Destination;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Producer {
//
//	@Autowired
//	private JmsMessagingTemplate jmsTemplate; 
//	
//	
//	public void send(Destination destination, final String message){
//		jmsTemplate.convertAndSend(destination,message);
//	}
//}
