package com.pm.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pm.main.dto.UserDto;
import com.pm.main.service.UserServiceInt;
import com.pm.main.util.MyResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = { "user", "auth" })
public class UserCtl {
	@Autowired
	UserServiceInt userServiceInt;

	@GetMapping("/emplist")
	@ResponseBody
	public MyResponse openEmpListPage(
			@RequestParam(defaultValue = "1") int page) {
		
		MyResponse rs = new MyResponse(true);
		
		List<UserDto> list_emp = userServiceInt.getAllEmployeesService();
		
		rs.setSuccess(true);
        rs.addData(list_emp);
		return rs;
	}

	@PostMapping("/loginForm")
	public String loginForm(@RequestParam("email1") String myemail, @RequestParam("pass1") String mypass, Model model,
			HttpSession session) {
		String page = "login";
		if (myemail.equals("admin@gmail.com") && mypass.equals("admin123")) {
			page = "profile-admin";
		} else {
			UserDto emp = userServiceInt.authenticate(myemail);
			if (emp != null && emp.getPassword().equals(mypass)) {
				session.setAttribute("session_employee", emp);
				page = "home-employee";
			} else {
				model.addAttribute("model_error", "Email id and password didnt matched");
				page = "login";
			}
		}
		return page;
	}

}
