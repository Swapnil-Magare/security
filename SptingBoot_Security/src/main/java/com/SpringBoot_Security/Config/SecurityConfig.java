package com.SpringBoot_Security.Config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // tells Spring to use your security configuration
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;

	/* • To customize it, you need to create a custom SecurityFilterChain bean */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Customize the security filter chain
		http.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request -> request.requestMatchers("register", "login").permitAll().anyRequest()
						.authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Disables CSRF for simplicity.
		// Defines security rules for requests.
		// Implements basic authentication
		// Ensures sessions are not stored, suitable for APIs.
		return http.build();
	}
	/*
	 * =============================================================================
	 */
	/*
	 * Configures the AuthenticationProvider for Spring Security: - Uses
	 * DaoAuthenticationProvider for database authentication. - Sets a password
	 * encoder (NoOpPasswordEncoder for plain text passwords). - Links the custom
	 * UserDetailsService to fetch user details.
	 */
//	@Bean
//	public AuthenticationProvider authenticationprovider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//		provider.setUserDetailsService(userDetailsService);
//		return provider;
//	}
	/*
	 * =============================================================================
	 */

	/*
	 * Configures the AuthenticationProvider for Spring Security: - Uses
	 * DaoAuthenticationProvider to authenticate users against a database. - Sets
	 * BCryptPasswordEncoder with a strength of 12 for securely hashing passwords. -
	 * Links the custom UserDetailsService to fetch user details from the database.
	 */
	@Bean
	public AuthenticationProvider authenticationprovider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Compare it with the stored hashed password.
		provider.setUserDetailsService(userDetailsService); // Custom user detail fetching
		System.out.println("AuthenticationProvider inside the Security Config user for");
		return provider;
	}

	/*
	 * Authetication Manager talks to Authentication Provider.
	 * =============================================================================
	 */
	@Bean
	public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	/*
	 * =============================================================================
	 */
	/*
	 * Default in-Memory Authentication. to provide your own username and password
	 */

//	@Bean
//	// This method defines an in-memory UserDetailsService bean for authentication.
//	public UserDetailsService userDetailsService() {
//
//	    // Create the first user with username "kiran", password "k@123", and role "USER".
//	    UserDetails user1 = User
//	            .withDefaultPasswordEncoder() 
//	            .username("kiran") 
//	            .password("k@123") 
//	            .roles("USER") 
//	            .build();
//
//	    // Create the second user with username "harsh", password "h@123", and role "ADMIN".
//	    UserDetails user2 = User
//	            .withDefaultPasswordEncoder()
//	            .username("harsh") 
//	            .password("h@123") 
//	            .roles("ADMIN")
//	            .build();
//
//		/*
//		 * Uses default password encoder (not recommended for production).
//		 * Username for the first user.
//		 * Password for the first user.
//		 * Role assigned to the first user.
//		 */
//	    
//	    // Return an InMemoryUserDetailsManager instance with the two users.
//	   
//	    return new InMemoryUserDetailsManager(user1, user2);
//	}

}
