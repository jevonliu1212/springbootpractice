package com.springboot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.springboot.statemachine.bean.Events;
import com.springboot.statemachine.bean.States;

@SpringBootApplication
//@EnableScheduling
//@EnableAsync
@EnableRetry
public class SpringbootFirstApplication  {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringbootFirstApplication.class, args);
	}

	@Primary
	@Bean
	public TaskExecutor primaryTaskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    return executor;
	}
}
