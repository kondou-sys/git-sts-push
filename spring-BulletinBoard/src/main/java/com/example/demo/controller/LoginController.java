package com.example.demo.controller;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Accesslist;
import com.example.demo.model.User;
import com.example.demo.repository.AccesslistRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserRepository UserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
	@Autowired
	AccesslistRepository AccesslistRepository;
    
    @GetMapping("/login")
    String login() {
        return "login";
    }
    
    @GetMapping("/logout")
    String logout(Authentication loginUser,@ModelAttribute Accesslist Accesslist,Model Accessmodel) {
    	
		Accesslist.setDatetime(new Date());
		Accesslist.setUsername(loginUser.getName());
		Accesslist.setAction("logout");
		AccesslistRepository.saveAndFlush(Accesslist);
    	
        return "login";
    }
    
    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/register")
    public String process(@Validated @ModelAttribute("user") User user,
            BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserRepository.save(user);

        return "redirect:/login?register";
    }
}
