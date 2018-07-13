package com.springboot.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import com.springboot.entity.Employee;

@Mapper
public interface EmployeeMapper {
    
	Employee get(@Param("id") long id);
	
	//@CacheEvict(value = "emp",key ="#id",allEntries=true)
	@Delete("delete from employee where id=#{id}")
	int delete(@Param("id") long id);
	
	
	//@CachePut(value = "emp",key="'emp'.concat(#e.id)")
	@Update("update employee set name = #{name,jdbcType=VARCHAR} , mobile=#{mobile,jdbcType=VARCHAR} where id = #{id,jdbcType=BIGINT}")
	int update(Employee e);
}
