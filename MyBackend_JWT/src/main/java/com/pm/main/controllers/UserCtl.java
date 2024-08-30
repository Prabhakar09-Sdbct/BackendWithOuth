package com.pm.main.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.main.form.UserForm;
import com.pm.main.service.UserServiceImpl;
import com.pm.main.util.JwtUtil;
import com.pm.main.util.MyResponse;

@RestController
@RequestMapping(value = { "user", "auth" })
public class UserCtl {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public MyResponse generateToken(@RequestBody @Validated UserForm userForm, BindingResult bindingResult)
			throws Exception {

		MyResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userForm.getEmail(), userForm.getPassword()));
		} catch (UsernameNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Bed Credential");
		}

		UserDetails userDetails = this.userServiceImpl.loadUserByUsername(userForm.getEmail());

		String token = this.jwtUtil.generateToken(userDetails);

		res.setSuccess(true);
		res.addData(token);
		res.addResult("loginId", userForm.getEmail());
		res.addMessage("Authentication success !");
		res.addResult("token", token);

		return res;
	}
	
	public static MyResponse validate(BindingResult bindingResult) {
		MyResponse res = new MyResponse(true);

		if (bindingResult.hasErrors()) {

			res.setSuccess(false);

			Map<String, String> errors = new HashMap<String, String>();

			List<FieldError> list = bindingResult.getFieldErrors();
			
			list.forEach(e -> {
				errors.put(e.getField(), e.getDefaultMessage());
			});
			res.addInputErrors(errors);
			res.addMessage("Input validation error");
		}
		return res;

	}


}
