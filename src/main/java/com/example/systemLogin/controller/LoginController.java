package com.example.systemLogin.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
    public String dashboard(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
    	model.addAttribute("nome", CookieService.getCookie(request, "nameUser"));
    	return "welcome";
    }
	
	@PostMapping("/logar")
	public String logged(User user, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
		 User loggedUser = ur.findByEmail(user.getEmail()); 
		    if (loggedUser  != null) {
		        String passwordEntered = user.getPassword();
		        String passwordEncrypted  = loggedUser.getPassword();

		
		        if (passwordEncoder.matches(passwordEntered, passwordEncrypted)) {
	    	CookieService.setCookie(response, "userId",String.valueOf(loggedUser.getId()), 100000);
	    	CookieService.setCookie(response, "nameUser",String.valueOf(loggedUser.getName()), 100000);
	        return "redirect:/";
		        }
		    }
	    model.addAttribute("erro", "Email ou senha inválidos!");
	    return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletResponse response) throws UnsupportedEncodingException {
	    CookieService.setCookie(response, "userId", "", 0);
	    return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String userRegistration(@Valid User user, BindingResult result, Model model) {
		if (ur.findByEmail(user.getEmail()) != null) {
	        model.addAttribute("erro", "Este e-mail já está cadastrado!");
	        return "cadastro";
	    }
		String encoder = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encoder);
		if(result.hasErrors()) {
			return "redirect:/register";
		}

		ur.save(user);
		return"redirect:/login";
	}
}
