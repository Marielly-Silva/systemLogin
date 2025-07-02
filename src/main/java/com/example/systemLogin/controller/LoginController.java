package com.example.systemLogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.systemLogin.model.User;
import com.example.systemLogin.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepository ur;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String userRegistration(@Valid User user, BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:/register";
		}

		ur.save(user);
		return"redirect:/login";
	}
}
