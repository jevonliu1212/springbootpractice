package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.entity.Account;

public interface AccountDao extends JpaRepository<Account,Integer>{

}
