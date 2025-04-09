package com.SpringBoot_Security.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot_Security.Model.Users;
import com.SpringBoot_Security.Service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		System.out.println("UserController" + user);
		return service.register(user);
	}

	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		System.out.println(user +"UserController");
		return "UserController" + service.verify(user) + "Login Succesfully  ";

	}
}
