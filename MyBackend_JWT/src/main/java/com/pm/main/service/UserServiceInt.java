package com.pm.main.service;

import java.util.List;

import com.pm.main.dto.UserDto;

public interface UserServiceInt 
{
	public UserDto authenticate(String email);
	public boolean addEmployeeService(UserDto emp);
	public List<UserDto> getAllEmployeesService();
	public boolean deleteEmployeeService(String email);
	public boolean updateEmployeeService(UserDto emp);
}
