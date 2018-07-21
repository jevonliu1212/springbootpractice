package com.springboot.service;

import java.util.Random;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

	private final static Logger logger = LoggerFactory.getLogger(AsyncService.class);
	
	@Async
	public Future<String> asyncTest(){
		logger.info("异步调用asyncTest..........开始");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("异步调用asyncTest..........结束");
		return new AsyncResult<String>("success");
	}
	
	@Retryable(value = { Exception.class }, maxAttempts = 3)
	public void retry() throws Exception {
		logger.info("retry.........");
		Random r = new Random();
		int n = r.nextInt(3);
		logger.info("num....{}",n);
		if(n!=1){
			int num =1/0;
		}
		logger.info("retry success.........");
	}
}
