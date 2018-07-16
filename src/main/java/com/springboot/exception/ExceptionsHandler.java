package com.springboot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String exceptionHander(Exception exception){
		System.out.println("exceptionHander...........");
		return exception.getMessage();
	}
}
