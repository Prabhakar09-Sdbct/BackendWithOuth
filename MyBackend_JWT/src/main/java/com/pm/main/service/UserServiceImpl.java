package com.pm.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pm.main.dao.UserDao;
import com.pm.main.dto.UserDto;

@Service
@Transactional
public class UserServiceImpl implements UserServiceInt, UserDetailsService
{
	@Autowired
	UserDao userDao;
	
	@Override
	public boolean addEmployeeService(UserDto emp) 
	{
		boolean status = false;
		try
		{
			userDao.save(emp);
			status = true;
		}
		catch(Exception e)
		{
			status = false;
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public List<UserDto> getAllEmployeesService()
	{
		return userDao.findAll();
	}
	
	@Override
	public UserDetails authenticate(String email) throws UsernameNotFoundException
	{
		UserDto emp = userDao.findByEmail(email);
		if(email.equals(emp.getEmail()))
		{
			return new User(emp.getEmail(), emp.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User Not Found !");
		}
	}
	
	@Override
	public boolean deleteEmployeeService(String email)
	{
		boolean status = false;
		try
		{
			userDao.deleteByEmail(email);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@Override
	public boolean updateEmployeeService(UserDto emp)
	{
		boolean status = false;
		
		try
		{
			UserDto existingEmp = userDao.findByEmail(emp.getEmail());
			if(existingEmp != null)
			{
				existingEmp.setName(emp.getName());
				existingEmp.setPassword(emp.getPassword());
				existingEmp.setPhoneno(emp.getPhoneno());
				
				userDao.save(existingEmp);
				status = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			status = false;
		}
		
		return status;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDto emp = userDao.findByEmail(email);
		if(email.equals(emp.getEmail()))
		{
			return new User(emp.getEmail(), emp.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User Not Found !");
		}
	}
}
