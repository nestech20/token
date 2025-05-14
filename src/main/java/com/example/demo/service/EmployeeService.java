package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Lazy
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Employee Register(EmployeeDTO dto) {

		String hashsedPassword = passwordEncoder.encode(dto.getPassword());
		Employee employee = new Employee();

		

		employee.setUsername(dto.getUsername());
		employee.setPassword(hashsedPassword);

		return employeeRepository.save(employee);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Employee employee = employeeRepository.findByUsername(username);
		 if(employee == null) {
			 throw new IllegalArgumentException("Employee with this username");
		 }
		 
		 
		return new EmployeePrinciple(employee);
	}

}
