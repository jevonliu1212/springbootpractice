package com.springboot.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

import com.alibaba.fastjson.JSON;
import com.springboot.entity.Account;

public class CsvItemProcessor extends ValidatingItemProcessor<Account> {

	private final static Logger logger = LoggerFactory.getLogger(CsvItemProcessor.class);
	
	@Override
	public Account process(Account arg0) throws ValidationException {
		logger.info("数据处理..............{}",JSON.toJSONString(arg0));
		arg0.setRemark("ok");
		return arg0;
	}

	
}
