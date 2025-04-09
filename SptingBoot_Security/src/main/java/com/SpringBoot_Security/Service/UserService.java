package com.SpringBoot_Security.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringBoot_Security.Model.Users;
import com.SpringBoot_Security.Repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;

	@Autowired
	AuthenticationManager authmanager;

	@Autowired
	private JWTService jwtService;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword())); // Encodes the password
		System.out.println("UserService" + user);
		return repo.save(user); // hashed password is then stored in the database.

	}

	public String verify(Users user) {
		Authentication authentication = authmanager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			System.out.println("UserService Success");
			return "_UserServie Success" + jwtService.generateToken(user.getUsername());
		}

		System.out.println("UserService Failed to validated !!!");
		return "Failed";
	}
}
