package com.springboot.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventHandler {

	@EventListener
	public void listener(ApplicationEvent event){
		System.out.println("application event。。。。。。。。。。。。。。。"+event.getClass().getName());
	}
}
