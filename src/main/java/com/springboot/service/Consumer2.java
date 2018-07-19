package com.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer2 {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer2.class);

	
	@JmsListener(destination = "testTopic")
	public void receive1(String msg) {
		logger.info("receive1 topic={}，处理开始。。。。", msg);

		
		logger.info("receive1 topic={}，处理结束。。。。", msg);
	}
	
	@JmsListener(destination = "testTopic")
	public void receive2(String msg) {
		logger.info("receive2 topic={}，处理开始。。。。", msg);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		logger.info("receive2 topic={}，处理结束。。。。", msg);
	}
}
