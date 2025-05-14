package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;


@RestController
@RequestMapping("/auth")
public class HeyContoller {

	@Autowired
	private EmployeeService service;
	         
	
	@GetMapping("/test")
	public String sayHello() {
        System.out.println("Endpoint /test was hit");
		return "Hello EveryOne";
	}
	
	@PostMapping("/add_emplyoee_usernameAndPassword")
	public Employee register(EmployeeDTO employeeDTO) {
		Employee response = service.Register(employeeDTO);
		return response;
	}
	

}
