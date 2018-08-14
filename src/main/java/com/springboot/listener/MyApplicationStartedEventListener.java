package com.springboot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
@Component
public class MyApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

	private final static Logger logger = LoggerFactory.getLogger(MyApplicationStartedEventListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent arg0) {
		SpringApplication app = arg0.getSpringApplication();
		app.setBannerMode(Banner.Mode.OFF);
        logger.info("==MyApplicationStartedEventListener==");
	}

}
