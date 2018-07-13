package com.springboot.tasks;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	@Scheduled(cron = "0 10/5 15 * * ?") 
	public void test(){
		System.out.println("定时任务..."+new Date());
	}
}
