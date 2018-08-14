package com.springboot.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

public class MyApplicationEvent extends ApplicationEvent{

	private static final long serialVersionUID = 6614677342705664940L;

	public MyApplicationEvent(Object source) {
		super(source);
	}


	
	
}
