package com.pm.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pm.main.dto.UserDto;


//@Repository
@Transactional
public interface UserDao extends JpaRepository<UserDto, Integer>
{
	UserDto findByEmail(String email);
	void deleteByEmail(String email);
}