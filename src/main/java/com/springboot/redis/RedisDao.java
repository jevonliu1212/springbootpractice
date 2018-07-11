package com.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisDao {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void set(String key,String value){
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		ops.set(key, value);
	}
	
	public String get(String key){
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		return ops.get(key);
	}
}
