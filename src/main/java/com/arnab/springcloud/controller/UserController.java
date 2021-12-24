package com.arnab.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.arnab.springcloud.model.User;
import com.arnab.springcloud.repository.UserRepository;
import com.arnab.springcloud.security.config.SecurityService;

@Controller
public class UserController {
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/showReg")
	public String showRegistrationPage() {
		return "registerUser";
	}
	@PostMapping("/registerUser")
	public String register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
		return "/login";
	}
	@GetMapping("/")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(String email,String password) {	
		boolean loginResponse = securityService.login(email, password);	
		if(loginResponse) {
			return "index";
		}
		return "login";
	}
}
