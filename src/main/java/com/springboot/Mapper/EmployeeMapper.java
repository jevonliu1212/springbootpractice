package com.springboot.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.springboot.entity.Employee;

@Mapper
public interface EmployeeMapper {
    
	Employee get(@Param("id") long id);
}
