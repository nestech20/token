package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class config {

	 @Autowired
	    private UserDetailsService employeeService;
	
	 	@Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf(csrf -> csrf.disable())
	        		.authorizeHttpRequests(auth -> auth
	        				.requestMatchers("/auth/test"
	        						+"").permitAll()
	        				.requestMatchers("/auth/add_emplyoee_usernameAndPassword","/swagger-ui.html","/swagger-ui/**",
	        						"/v3/api-docs/**","/v3/api-docs.yaml","/swagger-resources/**","/configuration/**",
	        						"/webjars/**").permitAll()
	        				.anyRequest().authenticated())
	            .userDetailsService(employeeService)
	            .httpBasic(Customizer.withDefaults());

	        return http.build();
	    }
	 
	 	@Bean
	 	public BCryptPasswordEncoder passwordEncoder() {
	 		return new BCryptPasswordEncoder(10);
	 	}
	 
	 	@Bean
	 	public AuthenticationProvider authenticationProvider() {
	 		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	 		provider.setPasswordEncoder(passwordEncoder());
	 		provider.setUserDetailsService(employeeService);
	 		return provider;
	 		
	 	}
}
