package com.pm.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.main.dao.UserDao;
import com.pm.main.dto.UserDto;

@Service
public class UserServiceImpl implements UserServiceInt
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
	public UserDto authenticate(String email)
	{
		UserDto emp = userDao.findByEmail(email);
		return emp;
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
}
