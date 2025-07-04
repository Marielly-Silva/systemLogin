package com.example.systemLogin.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.systemLogin.model.User;
import com.example.systemLogin.repository.UserRepository;
import com.example.systemLogin.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepository ur;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
    public String dashboard(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
    	model.addAttribute("nome", CookieService.getCookie(request, "nomeUsuario"));
    	return "welcome";
    }
	
	@PostMapping("/logar")
	public String logged(User user, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
	    User loggedUser = this.ur.login(user.getEmail(), user.getPassword());
	    if (loggedUser != null) {
	    	CookieService.setCookie(response, "userId",String.valueOf(loggedUser.getId()), 100000);
	    	CookieService.setCookie(response, "nameUser",String.valueOf(loggedUser.getName()), 100000);
	        return "redirect:/";
	    }
	    model.addAttribute("erro", "Email ou senha inválidos!");
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
