package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dao.AccountDao;
import com.springboot.entity.Account;
import com.springboot.redis.RedisDao;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	RedisDao redisDao;
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public void add(@RequestParam("name")String name,@RequestParam("money")double money){
		Account account = new Account();
		account.setName(name);
		account.setMoney(money);
		accountDao.save(account);
	}
	
	@RequestMapping(value = "/query",method = RequestMethod.POST)
	public Account query(@RequestParam("name")String name,@RequestParam("money")double money){
		return accountDao.findByNameAndMoney(name, money);
	}
	
	@RequestMapping(value = "/rset",method = RequestMethod.POST)
	public void rset(@RequestParam("key")String key,@RequestParam("value")String value){
		redisDao.set(key, value);
	}
	
	@RequestMapping(value = "/rget",method = RequestMethod.POST)
	public String rset(@RequestParam("key")String key){
	 return	redisDao.get(key);
	}
}
