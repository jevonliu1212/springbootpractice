package com.springboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(1)
public class CommandLineRunner1 implements CommandLineRunner {

	private final static Logger logger = LoggerFactory.getLogger(CommandLineRunner1.class);
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("CommandLineRunner1 running......");
	}

}
