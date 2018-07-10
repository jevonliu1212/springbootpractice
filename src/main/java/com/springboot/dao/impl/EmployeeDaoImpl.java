package com.springboot.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.springboot.dao.EmployeeDao;
import com.springboot.entity.Employee;
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Employee getById(Long id) {
		List<Employee> list = jdbcTemplate.query("select * from employee where id =?", new Object[]{id},new BeanPropertyRowMapper(Employee.class));
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}

}
