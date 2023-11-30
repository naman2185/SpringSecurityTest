package com.example.security.test.SpringSecurityTest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity   // for enabling @PreAuthorize in controller class
public class SecurityConfig {

	// for password encryption
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//for user configuration in InMemory
	@Bean
	public UserDetailsService userDetailsService() {
//		UserDetails publicUserDetails = User
//				.withUsername("public")
//				.password(passwordEncoder()
//						.encode("public"))
//				.roles("PUBLIC")
//				.build();
		UserDetails normalUserDetails = User.withUsername("normal")
				.password(passwordEncoder()
						.encode("normal"))
				.roles("NORMAL")
				.build();
		UserDetails adminUserDetails = User.withUsername("admin")
				.password(passwordEncoder()
						.encode("admin"))
				.roles("ADMIN")
				.build();
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(normalUserDetails, adminUserDetails);
		return inMemoryUserDetailsManager;
	}
	
	// for security and authorization configuration
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
		.authorizeHttpRequests()
		// used @Preuthoriaze in controller class
//		.requestMatchers("/home/admin")// requestMatchers("/home/admin/**") for all endpoints starting with /home/admin..
//		.hasRole("ADMIN")
//		
//		.requestMatchers("/home/normal")
//		.hasRole("NORMAL")
//		
//		.requestMatchers("/home/public")
//		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin();
		return httpSecurity.build();
	}
}
