package com.springboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(2)
public class ApplicationRunner3 implements ApplicationRunner {

	private final static Logger logger = LoggerFactory.getLogger(ApplicationRunner3.class);
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("applicationRunner3 running......");
	}

}
