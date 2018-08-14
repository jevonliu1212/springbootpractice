package com.springboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner1 implements ApplicationRunner {

	private final static Logger logger = LoggerFactory.getLogger(ApplicationRunner1.class);
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("applicationRunner1 running......");
	}

}
