package com.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

	@JmsListener(destination = "test")
	public void receive(String msg) {
		logger.info("消息={}，处理开始。。。。", msg);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("消息={}，处理结束。。。。", msg);
	}
	
}
