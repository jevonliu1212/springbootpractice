package com.springboot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunner2 implements CommandLineRunner {

	private final static Logger logger = LoggerFactory.getLogger(CommandLineRunner2.class);
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("CommandLineRunner2 running......");
	}

}
