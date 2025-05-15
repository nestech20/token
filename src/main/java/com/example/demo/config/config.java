package com.example.demo.config;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class config {

	 @Autowired
	    private UserDetailsService employeeService;
	 
	 @Autowired
	 private JwtAuthFilter jwtFilter;


	
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     http.csrf(csrf -> csrf.disable())
	         .authorizeHttpRequests(auth -> auth
	             .requestMatchers("/auth/**").permitAll()  // All auth related APIs open
	             .requestMatchers("/swagger-ui.html", "/swagger-ui/**",
	                              "/v3/api-docs/**", "/v3/api-docs.yaml",
	                              "/swagger-resources/**", "/configuration/**",
	                              "/webjars/**").permitAll() // swagger open too
	             .anyRequest().authenticated()             // rest need auth
	         )
	         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // add JWT filter
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
	 	
	 	@Bean
	 	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	 	    return config.getAuthenticationManager();
	 	}

}
