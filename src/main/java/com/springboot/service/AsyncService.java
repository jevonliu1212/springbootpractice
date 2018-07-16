package com.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

	private final static Logger logger = LoggerFactory.getLogger(AsyncService.class);
	
	@Async
	public void asyncTest(){
		logger.info("异步调用asyncTest..........开始");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("异步调用asyncTest..........结束");
	}
}
