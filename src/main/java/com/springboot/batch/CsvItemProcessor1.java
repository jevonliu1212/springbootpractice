package com.springboot.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

import com.alibaba.fastjson.JSON;
import com.springboot.entity.Account;
import com.springboot.entity.Employee;

public class CsvItemProcessor1 extends ValidatingItemProcessor<Employee> {

	private final static Logger logger = LoggerFactory.getLogger(CsvItemProcessor1.class);
	
	@Override
	public Employee process(Employee arg0) throws ValidationException {
		logger.info("数据处理..............{}",JSON.toJSONString(arg0));
		return arg0;
	}

	
}
